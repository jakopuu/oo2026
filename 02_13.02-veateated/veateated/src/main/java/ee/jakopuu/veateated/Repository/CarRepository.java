package ee.jakopuu.veateated.Repository;

import ee.jakopuu.veateated.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
