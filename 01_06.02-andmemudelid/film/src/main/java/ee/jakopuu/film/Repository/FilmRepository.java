package ee.jakopuu.film.Repository;

import ee.jakopuu.film.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepository extends JpaRepository<Product,Long> {
}
