package softuni.exam.models.dto.part;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartImportDto {

    @NotNull
    @Size(min = 2,max = 19)
    private String partName;

    @NotNull
    @Min(value = 10)
    @Max(value = 2000)
    private Double price;

    @NotNull
    @Min(value = 1)
    private Integer quantity;
}
