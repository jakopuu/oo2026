package ee.jakopuu.rentalstore.repository;

import ee.jakopuu.rentalstore.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmRepository extends JpaRepository<Film,Long> {
    List<Film> findByDays (Integer days);
}
