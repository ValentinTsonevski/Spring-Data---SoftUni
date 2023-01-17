package softuni.exam.models.dto.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.exam.models.dto.Mechanic.MechanicDto;
import softuni.exam.models.dto.car.CarDto;
import softuni.exam.models.dto.part.PartDto;
import softuni.exam.models.entity.Car;
import softuni.exam.models.entity.Mechanic;
import softuni.exam.models.entity.Part;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "task")
public class TaskImportDto {

    @Positive
    @NotNull
    @XmlElement
    private BigDecimal price;

    @NotNull
    @XmlElement
    private String date;

    @NotNull
    @XmlElement
    private MechanicDto mechanic;

    @NotNull
    @XmlElement
    private PartDto part;

    @NotNull
    @XmlElement
    private CarDto car;
}
