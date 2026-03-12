package ee.jakopuu.decathlon.controller;

import ee.jakopuu.decathlon.entity.Athlete;
import ee.jakopuu.decathlon.entity.Result;
import ee.jakopuu.decathlon.repository.AthleteRepository;
import ee.jakopuu.decathlon.repository.ResultRepository;
import ee.jakopuu.decathlon.service.DecathlonPointsCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("athletes")
public class AthleteController {

    @Autowired private AthleteRepository athleteRepository;
    @Autowired private ResultRepository resultRepository;
    @Autowired private DecathlonPointsCalculator calculator;

    @GetMapping
    public List<Athlete> getAthletes() {
        return athleteRepository.findAll();
    }

    @PostMapping
    public Athlete addAthlete(@RequestBody Athlete athlete) {
        if (athlete.getName() == null || athlete.getName().isBlank())
            throw new RuntimeException("Sportlase nimi ei tohi olla tühi");
        if (athlete.getId() != null)
            throw new RuntimeException("Uut sportlast ei saa lisada koos ID-ga");
        return athleteRepository.save(athlete);
    }

    @PostMapping("{id}/results")
    public Result addResult(@PathVariable Long id, @RequestBody Result result) {
        Athlete athlete = athleteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sportlast ei leitud ID-ga: " + id));

        if (result.getSpordiala() == null || result.getSpordiala().isBlank())
            throw new RuntimeException("Spordiala ei tohi olla tühi");
        if (result.getTulemus() <= 0)
            throw new RuntimeException("Tulemus peab olema suurem kui 0");

        int punktid = calculator.calculate(result.getSpordiala(), result.getTulemus());
        result.setPunktid(punktid);
        result.setAthlete(athlete);

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