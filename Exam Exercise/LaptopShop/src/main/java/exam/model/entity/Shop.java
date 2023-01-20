package exam.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "shops")
public class Shop extends BaseEntity{

    @Size(min = 4)
    @Column(nullable = false)
    private String name;

    @Min(value = 20000)
    @Column(nullable = false)
    private BigDecimal income;

    @Size(min = 4)
    @Column(nullable = false)
    private String address;

    @Min(value = 1)
    @Max(value = 50)
    @Column(name = "employee_count",nullable = false)
    private Integer employeeCount;

    @Min(value = 150)
    @Column(name = "shop_area",nullable = false)
    private Integer shopArea;

    @ManyToOne
    private Town town;
}
