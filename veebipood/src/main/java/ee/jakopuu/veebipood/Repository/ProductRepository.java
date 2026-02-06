package ee.jakopuu.veebipood.Repository;

import ee.jakopuu.veebipood.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
