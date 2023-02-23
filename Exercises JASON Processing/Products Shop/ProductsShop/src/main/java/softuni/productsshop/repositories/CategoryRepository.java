package softuni.productsshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.productsshop.entities.Category;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @Query(value = "select * from `products-shop`.categories order by RAND () LIMIT 1",
            nativeQuery = true)
    Optional<Category> getRandomEntity();
}
