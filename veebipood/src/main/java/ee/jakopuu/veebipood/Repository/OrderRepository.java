package ee.jakopuu.veebipood.Repository;

import ee.jakopuu.veebipood.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
