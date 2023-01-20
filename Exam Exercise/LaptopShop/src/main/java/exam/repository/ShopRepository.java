package exam.repository;

import exam.model.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//ToDo:
@Repository
public interface ShopRepository extends JpaRepository<Shop,Integer> {
    Optional<Shop> findFirstByName(String name);
}
