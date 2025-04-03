package com.easylink.easylink.controllers; //Updated 27.03

import java.util.Map;
import com.easylink.easylink.dtos.LoginDTO;
import com.easylink.easylink.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v3/auth")
public class AuthController {


    private final PersonService personService;

    @Autowired
    public AuthController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/login") //Updated 30.03.25
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        LoginDTO result = personService.login(loginDTO);

        if (result != null) {
            return ResponseEntity.ok(result); // 👈 JSON с firstName + email
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid email or password"));
        }
    }
}