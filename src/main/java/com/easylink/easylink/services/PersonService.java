package com.easylink.easylink.services;

import com.easylink.easylink.dtos.PersonDTO;
import com.easylink.easylink.dtos.LoginDTO;
import com.easylink.easylink.entities.Person;
import com.easylink.easylink.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder; //Added 27.03

@Service
public class PersonService {
    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired  //Updated 27.03
    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean createPerson(PersonDTO personDTO) {

        Person person = new Person();

        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setEmail(personDTO.getEmail()); //Updated 27.03
        person.setPassword(passwordEncoder.encode(personDTO.getPassword())); //Updated 27.03

        personRepository.save(person);

        return true;
    }

    public boolean updatePerson(String email, PersonDTO personDTO) {

        Person person = personRepository.findByEmail(email);

        if (person != null) {

            person.setFirstName(personDTO.getFirstName());

            try {
                personRepository.save(person);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    public LoginDTO login(LoginDTO loginDTO) { //Updated 30.03.25, заменил boolean на LoginDTO, чтобы вернуть не только Да или нет
        // но еще и firstName
        Person person = personRepository.findByEmail(loginDTO.getEmail());

        if (person != null && passwordEncoder.matches(loginDTO.getPassword(), person.getPassword())) {
            LoginDTO result = new LoginDTO();
            result.setEmail(person.getEmail());
            result.setFirstName(person.getFirstName()); // 🎯 вот имя
            return result;
        }

        return null; // логин не удался
    }
}