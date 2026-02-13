package ee.jakopuu.veebipood.Repository;

import ee.jakopuu.veebipood.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
