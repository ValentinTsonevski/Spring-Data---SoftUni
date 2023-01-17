package softuni.exam.models.dto.car;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.enums.CarType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "car")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarImportDto {

    @XmlElement
    @NotNull
    @Size(min = 2,max = 30)
    private String carMake;

    @XmlElement
    @NotNull
    @Size(min = 2,max = 30)
    private String carModel;

    @XmlElement
    @NotNull
    @Min(value = 1)
    private Integer year;

    @XmlElement
    @NotNull
    @Size(min = 2,max = 30)
    private String plateNumber;

    @XmlElement
    @NotNull
    @Min(value = 1)
    private Integer kilometers;

    @XmlElement
    @NotNull
    @Min(value = 1)
    private Double engine;

    @XmlElement
    @NotNull
    @Enumerated(EnumType.STRING)
    private CarType carType;
}
