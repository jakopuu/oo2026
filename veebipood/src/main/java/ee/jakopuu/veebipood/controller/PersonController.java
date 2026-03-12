package ee.jakopuu.veebipood.controller;


import ee.jakopuu.veebipood.Repository.PersonRepository;
import ee.jakopuu.veebipood.dto.PersonLoginRecordDto;
import ee.jakopuu.veebipood.entity.Person;
import ee.jakopuu.veebipood.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonController {

     @Autowired
    private PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("persons")
    public List<Person> getPersons(){
        return personRepository.findAll();
    }

    @GetMapping("persons/{id}")
    public List<Person> deletePerson(@PathVariable Long id){
        personRepository.deleteById(id);
        return personRepository.findAll();
    }

    @PostMapping("signup")
    public Person signup(@RequestBody Person person){
        personService.validate(person);
        return personRepository.save(person);
    }
    @PostMapping("login")
    public Person login(@RequestBody PersonLoginRecordDto personDto) {
        Person dbPerson =personRepository.findByEmail(personDto.email());
            if(dbPerson == null){
                throw new RuntimeException("Invalid email");
            }
        if(!dbPerson.getPassword().equals(personDto.password())){
            throw new RuntimeException("Invalid password");
        }
        return dbPerson;
    }
}