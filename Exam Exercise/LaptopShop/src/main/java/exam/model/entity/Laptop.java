package exam.model.entity;

import exam.enums.WarrantyType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "laptops")
public class Laptop extends BaseEntity{

    @Size(min = 9)
    @Column(name = "mac_address",unique = true,nullable = false)
    private String macAddress;

    @Column(name = "cpu_speed",nullable = false)
    private Double cpuSpeed;

    @Min(value = 8)
    @Max(value = 128)
    @Column(nullable = false)
    private Integer ram;

    @Min(value = 128)
    @Max(value = 1024)
    @Column(nullable = false)
    private Integer storage;

    @Size(min = 8)
    @Column(nullable = false,columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "warranty_type",nullable = false)
    private WarrantyType warrantyType;

    @ManyToOne
    private Shop shop;
}
