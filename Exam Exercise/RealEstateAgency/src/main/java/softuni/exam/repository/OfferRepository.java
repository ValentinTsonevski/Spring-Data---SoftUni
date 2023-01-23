package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.constants.ApartmentTypes;
import softuni.exam.models.entity.Offer;

import java.util.List;
import java.util.Optional;


@Repository
public interface OfferRepository extends JpaRepository<Offer, Integer> {

//    @Query("select ag.firstName,ag.lastName,o.id,ap.area,ap.town.townName,o.price from Agent as ag" +
//            " join Offer as o" +
//            " join Apartment as ap" +
//            " where ap.apartmentType = :apType order by ap.area desc, o.price asc")
Optional<List<Offer>> findAllByApartment_ApartmentTypeIsOrderByApartmentAreaDescPriceAsc(ApartmentTypes apType);

}
