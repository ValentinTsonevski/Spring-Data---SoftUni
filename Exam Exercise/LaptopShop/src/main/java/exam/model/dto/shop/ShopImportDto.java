package exam.model.dto.shop;

import exam.model.dto.town.TownDto;
import exam.model.entity.Town;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "shop")
@XmlAccessorType(XmlAccessType.FIELD)
public class ShopImportDto {

    @NotNull
    @XmlElement
    @Size(min = 4)
    private String name;

    @NotNull
    @XmlElement
    @Min(value = 20000)
    private BigDecimal income;

    @NotNull
    @XmlElement
    @Size(min = 4)
    private String address;

    @NotNull
    @XmlElement(name = "employee-count")
    @Min(value = 1)
    @Max(value = 50)
    private Integer employeeCount;

    @NotNull
    @XmlElement(name = "shop-area")
    @Min(value = 150)
    private Integer shopArea;

    @NotNull
    @XmlElement
    private TownDto town;
}
