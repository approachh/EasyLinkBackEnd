package com.easylink.easylink.services;

import com.easylink.easylink.dtos.AssociativeEntryDTO;
import com.easylink.easylink.dtos.SignUpDTO;
import com.easylink.easylink.entities.AssociativeEntry;
import com.easylink.easylink.entities.VibeAccount;
import com.easylink.easylink.repositories.VibeAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

@Service
@RequiredArgsConstructor
public class VibeAccountService {
    @Autowired
    private final VibeAccountRepository vibeAccountRepository;
    private ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    private AssociativeEntry createAssociativeEntry(AssociativeEntryDTO associativeEntryDTO){

        AssociativeEntry associativeEntry = modelMapper.map(associativeEntryDTO,AssociativeEntry.class);

        String hashedAnswer = passwordEncoder.encode(associativeEntryDTO.getAnswer());

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

        vibeAccount.setAssociativeEntries(entries);

        return vibeAccountRepository.save(vibeAccount)!=null;
    }
}
