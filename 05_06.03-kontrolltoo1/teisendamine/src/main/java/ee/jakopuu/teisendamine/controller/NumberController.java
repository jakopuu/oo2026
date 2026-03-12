package ee.jakopuu.teisendamine.controller;

import ee.jakopuu.teisendamine.entity.Number;
import ee.jakopuu.teisendamine.repository.NumberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NumberController {

    @Autowired
    private NumberRepository numberRepository;

    @GetMapping("numbers")
    public List<Number> getNumbers(){
        return numberRepository.findAll();
    }

    @PostMapping("numbers")
    public List<Number> addNumber(@RequestBody Number number){
        if(number.getArv() <= 0 ){
            throw new RuntimeException("Number has to be bigger then 0");
        }
        if (number.getId() != null) {
            throw new RuntimeException("Cannot add with ID");
        }

        numberRepository.save(number);
        return numberRepository.findAll();}
    @GetMapping("numbers/teisenda")
    public String teisenda(
            @RequestParam Long id,
            @RequestParam String teisendus) {

        Number number = numberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Number not found with ID: " + id));

        int arv = number.getArv();

        return switch (teisendus.toLowerCase()) {
            case "binaarne"          -> Integer.toBinaryString(arv);
            case "oktaalne"          -> Integer.toOctalString(arv);
            case "heksadetsimaalne"  -> Integer.toHexString(arv);
            default -> throw new RuntimeException(
                    "Vigane teisendus. Kasuta: binaarne, oktaalne või heksadetsimaalne"
            );
        };
    }
}

