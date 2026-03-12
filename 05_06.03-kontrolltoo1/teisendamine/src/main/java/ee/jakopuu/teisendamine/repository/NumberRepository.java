package ee.jakopuu.teisendamine.repository;

import ee.jakopuu.teisendamine.entity.Number;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NumberRepository extends JpaRepository<Number,Long> {
}
