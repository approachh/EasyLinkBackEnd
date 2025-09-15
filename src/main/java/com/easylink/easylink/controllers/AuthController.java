package com.easylink.easylink.controllers;

import java.util.List;
import java.util.Map;
import java.util.Collections;

import com.easylink.easylink.dtos.AssociativeLoginRequestDTO;
import com.easylink.easylink.dtos.AssociativeQuestionDTO;
import com.easylink.easylink.dtos.QuestionTemplateDTO;
import com.easylink.easylink.dtos.SignUpDTO;
import com.easylink.easylink.services.PersonService;
import com.easylink.easylink.services.QuestionTemplateService;
import com.easylink.easylink.services.VibeAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v3/auth")
public class AuthController {

    private final VibeAccountService vibeAccountService;
    private final PersonService personService;
    private final QuestionTemplateService questionTemplateService;

    @Value("${app.frontend.base-url:http://localhost:5173}")
    private String frontendBaseUrl;

    @PostMapping("/signup")
    public ResponseEntity<?> createVibeAccount(@RequestBody @Valid SignUpDTO signUpDTO) {
        boolean created = vibeAccountService.createVibeAccount(signUpDTO);
        if (created) {
            return ResponseEntity.ok(Map.of("message", "Verification email sent successfully!"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", "The account was not created"));
    }

    @PostMapping("/start")
    public ResponseEntity<?> startAuth(@RequestBody Map<String, String> payload) {
        List<AssociativeQuestionDTO> result = vibeAccountService.startAuth(payload);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkAnswers(@RequestBody @Valid AssociativeLoginRequestDTO associativeLoginRequestDTO) {
        String email = vibeAccountService.checkAnswers(associativeLoginRequestDTO);
        String token = vibeAccountService.generateToken(email);

        return ResponseEntity.ok(Map.of(
                "Authentication successful", token,   
                "message", "Authentication successful",
                "token", token   
        ));
    }

    @GetMapping("/question-templates")
    public ResponseEntity<?> getAllQuestionTemplates() {
        List<QuestionTemplateDTO> items = questionTemplateService.getAllQuestionTemplates();
        return ResponseEntity.ok(items);
    }

    @GetMapping("/verify-email")
    public ResponseEntity<?> verifyEmail(@RequestParam("token") String token) {
        boolean success = vibeAccountService.verifyEmail(token);
        if (success) {
            return ResponseEntity.status(HttpStatus.FOUND) // 302
                    .header("Location", frontendBaseUrl + "/email-verified")
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid or expired token."));
        }
    }
}
