package com.easylink.easylink.vibe_service.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class CodeGenerationService {

    private final Random random = new Random();

    private boolean isForbidden(String code){

        Set<String> blackList = Set.of("1111","1234","2222","3333","4444","5555");

        if(blackList.contains(code))return true;

        char first = code.charAt(0);
        if(code.chars().allMatch(c->c==first)) return true;

        return false;
    }

    private String generateCandidateCode() {

        return String.valueOf(1000 + random.nextInt(9000));

    }


    public String generateUniqueCode(Predicate<String> existsPredicate){

        String code;

        do {
            code = generateCandidateCode();
        } while (
                existsPredicate.test(code) || isForbidden(code)
        );

        return code;
    }
}
