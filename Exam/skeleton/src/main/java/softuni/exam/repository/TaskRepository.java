package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.enums.CarType;
import softuni.exam.models.entity.Task;

import java.util.List;
import java.util.Optional;

// TODO:
@Repository
public interface TaskRepository extends JpaRepository<Task,Integer> {

    Optional<Task> findFirstByMechanicFirstName(String name);

    Optional<List<Task>> findAllByCar_CarTypeOrderByPriceDesc(CarType carType);
}
