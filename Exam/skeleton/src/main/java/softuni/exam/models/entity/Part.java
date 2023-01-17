package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "parts")
public class Part extends BaseEntity{

    @Size(min = 2,max = 19)
    @Column(name = "part_name",nullable = false,unique = true)
    private String partName;

    @Min(value = 10)
    @Max(value = 2000)
    @Column(nullable = false)
    private Double price;

    @Min(value = 1)
    @Column
    private Integer quantity;
}
