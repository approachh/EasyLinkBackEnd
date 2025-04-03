package com.easylink.easylink.controllers;

import com.easylink.easylink.dtos.PersonDTO;

import com.easylink.easylink.services.PersonService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v3/person-management")
public class PersonController {
    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService){
        this.personService=personService;
    }

    @PostMapping
    public ResponseEntity createPerson(@RequestBody PersonDTO personDTO){

        boolean created = personService.createPerson(personDTO);

        if(created){
            return ResponseEntity.ok(Map.of("message","Person is created"));
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Person is not created");
        }
    }

    @PatchMapping("/{email}")
    public ResponseEntity updatePerson(@PathVariable String email, @RequestBody PersonDTO personDTO){

        boolean updated = personService.updatePerson(email,personDTO);

        return ResponseEntity.ok("Person is updated");
    }

}

//    @DeleteMapping("/{id}}")
//    public boolean deletePerson(){
//        return true;
//    }

//}
