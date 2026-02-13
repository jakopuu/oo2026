package ee.jakopuu.film.Repository;

import ee.jakopuu.film.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Movie,Long> {
}
