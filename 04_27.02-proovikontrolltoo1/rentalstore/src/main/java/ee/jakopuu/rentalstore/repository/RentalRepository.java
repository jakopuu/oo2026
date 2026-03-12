package ee.jakopuu.rentalstore.repository;

import ee.jakopuu.rentalstore.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {
}
