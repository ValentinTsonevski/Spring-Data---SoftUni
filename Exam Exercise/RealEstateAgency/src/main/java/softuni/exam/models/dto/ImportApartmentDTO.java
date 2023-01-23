package softuni.exam.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.constants.ApartmentTypes;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "apartment")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImportApartmentDTO {

    @NotNull
    @XmlElement
    private ApartmentTypes apartmentType;

    @NotNull
    @XmlElement
    @DecimalMin(value = "40")
    private double area;

    @NotNull
    @XmlElement
    private String town;

}
