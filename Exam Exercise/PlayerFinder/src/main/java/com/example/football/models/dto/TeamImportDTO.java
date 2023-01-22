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
@AllArgsConstructor
public class TeamImportDTO {

    @NotNull
    @Size(min = 3)
    private String name;

    @NotNull
    @Size(min = 3)
    private String stadiumName;

    @NotNull
    @Min(value = 1000)
    private Integer fanBase;

    @NotNull
    @Size(min = 10)
    private String history;

    @NotNull
    private String townName;

    public String getTownName() {
        return townName;
    }
}
