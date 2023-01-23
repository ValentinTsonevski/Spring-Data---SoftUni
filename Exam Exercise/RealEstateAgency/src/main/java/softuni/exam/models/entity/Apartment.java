package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.constants.ApartmentTypes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "apartments")
public class Apartment extends BaseEntity{

    @Column(name = "apartment_type",nullable = false)
    private ApartmentTypes apartmentType;

    @Min(value = 40)
    @Column(nullable = false)
    private double area;

    @ManyToOne
    private Town town;
}
