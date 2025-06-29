package com.easylink.easylink.controllers; //Updated 27.03

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.easylink.easylink.dtos.AssociativeLoginRequestDTO;
import com.easylink.easylink.dtos.AssociativeQuestionDTO;
import com.easylink.easylink.dtos.QuestionTemplateDTO;
import com.easylink.easylink.dtos.SignUpDTO;
import com.easylink.easylink.services.JwtService;
import com.easylink.easylink.services.PersonService;
import com.easylink.easylink.services.QuestionTemplateService;
import com.easylink.easylink.services.VibeAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v3/auth")
public class AuthController {
    private final VibeAccountService vibeAccountService;
    private final PersonService personService;
    private final QuestionTemplateService questionTemplateService;



//    @Autowired
//    public AuthController(PersonService personService) {
//        this.personService = personService;
//    }
//
//    @PostMapping("/login") //Updated 30.03.25
//    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
//        LoginDTO result = personService.login(loginDTO);
//
//        if (result != null) {
//            return ResponseEntity.ok(result); // 👈 JSON с firstName + email
//        } else {
//            return ResponseEntity
//                    .status(HttpStatus.UNAUTHORIZED)
//                    .body(Map.of("message", "Invalid email or password"));
//        }
//    }

    @PostMapping("/signup")
    public ResponseEntity createVibeAccount(@RequestBody @Valid SignUpDTO signUpDTO){

        boolean created = vibeAccountService.createVibeAccount(signUpDTO);

        if (created){
            return ResponseEntity.ok("The account is created");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The account was not created");
    }

    @PostMapping("/start")
    public ResponseEntity<?> startAuth(@RequestBody Map<String,String> payload){

        List<AssociativeQuestionDTO> result  = vibeAccountService.startAuth(payload);

        if(result.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NO questions found");
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/check")
    public ResponseEntity<?> checkAnswers(@RequestBody @Valid AssociativeLoginRequestDTO associativeLoginRequestDTO){

        String email = vibeAccountService.checkAnswers(associativeLoginRequestDTO);

        String token = vibeAccountService.generateToken(email);

       return ResponseEntity.ok(Map.of("Authentication successful",token));
    }

    @GetMapping("/question-templates")
    public ResponseEntity<List<QuestionTemplateDTO>> getAllQuestionTemplates() {

        List<QuestionTemplateDTO> questionTemplateDTOS = questionTemplateService.getAllQuestionTemplates();
        if (questionTemplateDTOS.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.ok(questionTemplateDTOS); // 200
    }


}