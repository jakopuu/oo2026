package ee.jakopuu.decathlon.controller;

import ee.jakopuu.decathlon.dto.AthleteCreateRequest;
import ee.jakopuu.decathlon.dto.ResultCreateRequest;
import ee.jakopuu.decathlon.entity.Athlete;
import ee.jakopuu.decathlon.entity.Result;
import ee.jakopuu.decathlon.repository.AthleteRepository;
import ee.jakopuu.decathlon.repository.ResultRepository;
import ee.jakopuu.decathlon.service.DecathlonPointsCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("athletes")
@CrossOrigin(origins = "*")
public class AthleteController {

    @Autowired private AthleteRepository athleteRepository;
    @Autowired private ResultRepository resultRepository;
    @Autowired private DecathlonPointsCalculator calculator;

    // localhost:8080/athletes?page=0&size=5&sort=totalPoints,desc
    // localhost:8080/athletes?page=0&size=5&country=Estonia
    @GetMapping
    public Page<Athlete> getAthletes(@RequestParam(required = false) String country, Pageable pageable) {
        if (country != null && !country.isBlank()) {
            return athleteRepository.findByCountry(country, pageable);
        }
        return athleteRepository.findAll(pageable);
    }

    @PostMapping
    public Athlete addAthlete(@RequestBody AthleteCreateRequest request) {
        if (request.name() == null || request.name().isBlank())
            throw new RuntimeException("Sportlase nimi ei tohi olla tühi");

        Athlete athlete = new Athlete();
        athlete.setName(request.name());
        athlete.setCountry(request.country());
        return athleteRepository.save(athlete);
    }

    @PostMapping("{id}/results")
    public Result addResult(@PathVariable Long id, @RequestBody ResultCreateRequest request) {
        Athlete athlete = athleteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sportlast ei leitud ID-ga: " + id));

        if (request.spordiala() == null || request.spordiala().isBlank())
            throw new RuntimeException("Spordiala ei tohi olla tühi");
        if (request.tulemus() <= 0)
            throw new RuntimeException("Tulemus peab olema suurem kui 0");

        int punktid = calculator.calculate(request.spordiala(), request.tulemus());

        Result result = new Result();
        result.setSpordiala(request.spordiala());
        result.setTulemus(request.tulemus());
        result.setPunktid(punktid);
        result.setAthlete(athlete);

        // totalPoints hoitakse Athlete peal otse, et sellega saaks Pageable'i sort-parameetriga sortida
        athlete.setTotalPoints(athlete.getTotalPoints() + punktid);
        athleteRepository.save(athlete);

        return resultRepository.save(result);
    }

    @GetMapping("{id}/results/sum")
    public int getSum(@PathVariable Long id) {
        athleteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sportlast ei leitud ID-ga: " + id));
        Integer sum = resultRepository.sumPunktidByAthleteId(id);
        return sum != null ? sum : 0;
    }
}