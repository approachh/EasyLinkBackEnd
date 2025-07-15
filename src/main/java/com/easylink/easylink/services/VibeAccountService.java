package com.easylink.easylink.services;

import com.easylink.easylink.dtos.*;
import com.easylink.easylink.entities.AssociativeEntry;
import com.easylink.easylink.entities.QuestionTemplate;
import com.easylink.easylink.entities.VibeAccount;
import com.easylink.easylink.exceptions.IncorrectAnswerException;
import com.easylink.easylink.exceptions.UserLockedException;
import com.easylink.easylink.repositories.AssociativeEntryRepository;
import com.easylink.easylink.repositories.QuestionTemplateRepository;
import com.easylink.easylink.repositories.VibeAccountRepository;
import com.easylink.easylink.vibe_service.application.service.AmplitudeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

@Service
@RequiredArgsConstructor
public class VibeAccountService {

    private final VibeAccountRepository vibeAccountRepository;
    private final AssociativeEntryRepository associativeEntryRepository;
    private final QuestionTemplateRepository questionTemplateRepository;
    private final AmplitudeService amplitudeService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final Duration LOCK_DURATION = Duration.ofMinutes(30);

    private final JwtService jwtService;

    private AssociativeEntry createAssociativeEntry(AssociativeEntryDTO dto) {

        AssociativeEntry associativeEntry = modelMapper.map(dto, AssociativeEntry.class);

        String text = dto.getRealQuestion().getText();
        boolean isPredefined = dto.getRealQuestion().isPredefined();


        QuestionTemplate questionTemplate = questionTemplateRepository.findByText(text)
                .orElseGet(() -> {
                    QuestionTemplate qt = new QuestionTemplate();
                    qt.setText(text);
                    qt.setPredefined(isPredefined);
                    qt.setCreatedAt(LocalDateTime.now());
                    return questionTemplateRepository.save(qt);
                });

        String hashedAnswer = passwordEncoder.encode(dto.getAnswer());

        associativeEntry.setRealQuestion(questionTemplate);
        associativeEntry.setAnswerHash(hashedAnswer);

        return associativeEntry;
    }


    public boolean createVibeAccount(SignUpDTO signUpDTO){

        VibeAccount vibeAccount = new VibeAccount();
        vibeAccount.setCreated(LocalDateTime.now());
        vibeAccount.setLastLogin(LocalDateTime.now());
        vibeAccount.setEmail(signUpDTO.getEmail());

        List<AssociativeEntry> entries = signUpDTO.getEntries()
                .stream()
                .map(this::createAssociativeEntry)
                .collect(Collectors.toList());

        entries.forEach(e -> e.setVibeAccount(vibeAccount));

        vibeAccount.setAssociativeEntries(entries);

        amplitudeService.sendEvent(signUpDTO.getEmail(),"User created", Map.of(
                "source", "backend",
                "email",signUpDTO.getEmail()
        ));



        return vibeAccountRepository.save(vibeAccount) != null;
    }



    private List<AssociativeEntry> getRandomQuestionByEmail(String email){

        Optional<VibeAccount> accountVibe = vibeAccountRepository.findByEmail(email);

        if(accountVibe.isEmpty()) return List.of();

        List<AssociativeEntry> associativeEntryList = accountVibe.get().getAssociativeEntries();

        if(associativeEntryList==null || associativeEntryList.isEmpty()) return List.of();

        int maxCount = Math.min(3,associativeEntryList.size());

        int count = new Random().nextInt(maxCount) + 1;

        Collections.shuffle(associativeEntryList);

        //return associativeEntryList.subList(0,count);
        return associativeEntryList;
    }

    public List<AssociativeQuestionDTO> startAuth(Map<String,String> payload){

        String email = payload.get("email");

        List<AssociativeEntry> associativeEntryQuestions = getRandomQuestionByEmail(email);

        return associativeEntryQuestions.stream()
                .map(entry->new AssociativeQuestionDTO(entry.getId(),entry.getAssociativeQuestion()))
                .toList();
    }

    private void validateRequest(AssociativeLoginRequestDTO requestDTO){

        if(requestDTO == null || requestDTO.getAnswers() == null || requestDTO.getAnswers().isEmpty()){

            throw new IllegalArgumentException("Answers list must not be empty");
        }
    }

    private List<AssociativeEntry> loadEntries(AssociativeLoginRequestDTO requestDTO){

        List<UUID> uuidList = requestDTO.getAnswers().stream().map(AssociativeAnswerDTO::getEntryId).toList();

        List<AssociativeEntry> associativeEntryList = associativeEntryRepository.findAllById(uuidList);

        return associativeEntryList;
    }

    private boolean verifyAnswers(List<AssociativeEntry> associativeEntryList, AssociativeLoginRequestDTO requestDTO){

        List<String> inputAnswers = requestDTO.getAnswers().stream().map(a -> a.getAnswer().trim()).toList();

        return associativeEntryList.stream().allMatch(entry ->
                inputAnswers.stream().anyMatch(input -> passwordEncoder.matches(input, entry.getAnswerHash()))
        );
    }

    private void checkLock(List<AssociativeEntry> associativeEntryList, String timezone) {
        Instant lockTime = associativeEntryList.getFirst().getVibeAccount().getLockTime();

        if (lockTime != null) {
            Instant now = Instant.now();

            if (lockTime.isAfter(now)) {

                ZoneId userZone = ZoneId.of(timezone);
                ZonedDateTime userLockTime = lockTime.atZone(userZone);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy hh:mm a");
                String formattedLockTime = userLockTime.format(formatter);

                throw new UserLockedException("Account is locked until " + formattedLockTime + " (" + timezone + ")");
            }
        }
    }



    private void handleFailedLogin(VibeAccount vibeAccount) {
        vibeAccount.setFailedAttempts(vibeAccount.getFailedAttempts() + 1);

        if (vibeAccount.getFailedAttempts() >= MAX_FAILED_ATTEMPTS) {
            vibeAccount.setLockTime(Instant.now().plus(LOCK_DURATION)); // 👈 теперь по UTC
        }

        vibeAccountRepository.save(vibeAccount);
    }



    private void checkAnswers(List<AssociativeEntry> associativeEntryList,AssociativeLoginRequestDTO associativeLoginRequestDTO){

        if(!verifyAnswers(associativeEntryList,associativeLoginRequestDTO)){

            VibeAccount vibeAccount = associativeEntryList.get(0).getVibeAccount();

            handleFailedLogin(vibeAccount);

            throw new IncorrectAnswerException("Your answers are incorrect.");

        }
    }

    public String checkAnswers(AssociativeLoginRequestDTO associativeLoginRequestDTO){

        amplitudeService.sendEvent(associativeLoginRequestDTO.getEmail(),"User login", Map.of(
                "source", "backend",
                "email",associativeLoginRequestDTO.getEmail()
        ));

        validateRequest(associativeLoginRequestDTO);

        List<AssociativeEntry> associativeEntryList = loadEntries(associativeLoginRequestDTO);

        checkLock(associativeEntryList,associativeLoginRequestDTO.getTimezone());

        checkAnswers(associativeEntryList,associativeLoginRequestDTO);

        return associativeLoginRequestDTO.getEmail();
    }

    public String generateToken(String email){
        Optional<VibeAccount> vibeAccount = vibeAccountRepository.findByEmail(email);
        if(vibeAccount.isPresent()){
            return jwtService.generateToken(String.valueOf(vibeAccount.get().getId()));
        }else{
            return null;
        }
    }
}
