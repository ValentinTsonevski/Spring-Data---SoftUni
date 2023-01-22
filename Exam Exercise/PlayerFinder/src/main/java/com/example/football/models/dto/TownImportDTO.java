package com.example.football.models.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class TownImportDTO {

    @NotNull
    @Size(min = 2)
    private String name;

    @NotNull
    @Min(value = 1)
    private Integer population;

    @NotNull
    @Size(min = 10)
    private String travelGuide;
}
