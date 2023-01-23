package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "towns")
public class Town extends BaseEntity{

    @Size(min = 2)
    @Column(name = "town_name",unique = true,nullable = false)
    private String townName;

    @Min(value = 1)
    @Column(nullable = false)
    private Integer population;
}
