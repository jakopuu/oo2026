package ee.jakopuu.decathlon.repository;

import ee.jakopuu.decathlon.entity.Athlete;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteRepository extends JpaRepository<Athlete, Long> {

    Page<Athlete> findByCountry(String country, Pageable pageable);
}