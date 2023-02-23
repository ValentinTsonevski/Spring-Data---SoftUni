package softuni.productsshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.productsshop.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    @Query(value = "select * from `products-shop`.users order by RAND () LIMIT 1",
            nativeQuery = true)
    Optional<User> getRandomEntity();


    Optional<List<User>> findAllBySoldProductsIsNotNullAndSoldProductsBuyerLastNameIsNotNullOrderBySoldProductsBuyerFirstName();
    //findAllByProductsSoldIsNotNullAndProductsSoldBuyerLastNameIsNotNullOrderByProductsSoldBuyerFirstName
    //getAllByProductsSoldBuyerIsNotNullOrderByProductsSoldBuyerFirstName

}
