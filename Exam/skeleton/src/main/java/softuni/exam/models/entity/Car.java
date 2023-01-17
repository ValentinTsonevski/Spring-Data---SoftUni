package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.enums.CarType;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "car_type",nullable = false)
    private CarType carType;

    @Size(min = 2,max = 30)
    @Column(name = "car_make",nullable = false)
    private String carMake;

    @Size(min = 2,max = 30)
    @Column(name = "car_model",nullable = false)
    private String carModel;

    @Min(value = 1)
    @Column(nullable = false)
    private Integer year;

    @Size(min = 2,max = 30)
    @Column(name = "plate_number",nullable = false,unique = true)
    private String plateNumber;

    @Min(value = 1)
    @Column(nullable = false)
    private Integer kilometers;

    @Min(value = 1)
    @Column(nullable = false)
    private Double engine;
}
