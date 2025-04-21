package com.easylink.easylink.services;

import com.easylink.easylink.dtos.*;
import com.easylink.easylink.entities.AssociativeEntry;
import com.easylink.easylink.entities.QuestionTemplate;
import com.easylink.easylink.entities.VibeAccount;
import com.easylink.easylink.repositories.AssociativeEntryRepository;
import com.easylink.easylink.repositories.VibeAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

@Service
@RequiredArgsConstructor
public class VibeAccountService {
    @Autowired
    private final VibeAccountRepository vibeAccountRepository;
    private final AssociativeEntryRepository associativeEntryRepository;
    @Autowired
    private ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    private AssociativeEntry createAssociativeEntry(AssociativeEntryDTO associativeEntryDTO){

        AssociativeEntry associativeEntry = modelMapper.map(associativeEntryDTO,AssociativeEntry.class);

        QuestionTemplate questionTemplate = new QuestionTemplate();
        questionTemplate.setText(associativeEntryDTO.getRealQuestion());
        questionTemplate.setCreatedAt(LocalDateTime.now());
        questionTemplate.setPredefined(false);

        String hashedAnswer = passwordEncoder.encode(associativeEntryDTO.getAnswer());

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

    public boolean checkAnswers(AssociativeLoginRequestDTO associativeLoginRequestDTO){

        List<AssociativeAnswerDTO> associativeAnswerDTOS = associativeLoginRequestDTO.getAnswers();

        if(associativeAnswerDTOS==null || associativeAnswerDTOS.isEmpty()) return false;

        List<UUID> uuidList = associativeAnswerDTOS.stream().map(AssociativeAnswerDTO::getEntryId).toList();
        List<String> inputAnswers = associativeAnswerDTOS.stream().map(a -> a.getAnswer().trim()).toList();

        List<AssociativeEntry> associativeEntryList = associativeEntryRepository.findAllById(uuidList);

        if(associativeEntryList.size()!=associativeAnswerDTOS.size()) return false;

        return associativeEntryList.stream().allMatch(entry ->
                inputAnswers.stream().anyMatch(input -> passwordEncoder.matches(input, entry.getAnswerHash()))
        );
    }
}
