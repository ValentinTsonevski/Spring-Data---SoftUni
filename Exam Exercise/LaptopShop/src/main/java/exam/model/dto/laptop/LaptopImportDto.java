package exam.model.dto.laptop;

import exam.enums.WarrantyType;
import exam.model.dto.shop.ShopDto;
import exam.model.entity.Shop;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaptopImportDto {

    @NotNull
    @Size(min = 9)
    private String macAddress;

    @NotNull
    private Double cpuSpeed;

    @NotNull
    @Min(value = 8)
    @Max(value = 128)
    private Integer ram;

    @NotNull
    @Min(value = 128)
    @Max(value = 1024)
    private Integer storage;

    @NotNull
    @Size(min = 8)
    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    @Enumerated(EnumType.STRING)
    private WarrantyType warrantyType;

    @NotNull
    private ShopDto shop;
}
