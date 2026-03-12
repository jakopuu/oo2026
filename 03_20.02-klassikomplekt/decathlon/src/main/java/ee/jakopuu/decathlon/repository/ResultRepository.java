package ee.jakopuu.decathlon.repository;

import ee.jakopuu.decathlon.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResultRepository extends JpaRepository<Result, Long> {
    @Query("SELECT SUM(r.punktid) FROM Result r WHERE r.athlete.id = :athleteId")
    Integer sumPunktidByAthleteId(Long athleteId);
}