package softuni.productsshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import softuni.productsshop.entities.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    @Query(value = "select * from `products-shop`.products order by RAND () LIMIT 1",
            nativeQuery = true)
    Optional<Product> getRandomEntity();

    Optional<List<Product>> getAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(BigDecimal low,BigDecimal high);

}
