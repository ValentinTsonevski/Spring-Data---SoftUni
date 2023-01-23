package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Apartment;

import java.util.Optional;


@Repository
public interface ApartmentRepository  extends JpaRepository<Apartment, Integer> {

    @Query("select a from Apartment as a where a.area = :area and a.town.townName = :townName")
    Optional<Apartment> getApartmentByAreaAndTownTownName(double area, String townName);

    Optional<Apartment> findById(Integer id);


}
