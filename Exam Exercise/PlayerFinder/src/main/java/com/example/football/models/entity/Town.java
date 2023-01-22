package com.example.football.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "towns")
public class Town extends BaseEntity{

    @Size(min = 2)
    @Column(nullable = false,unique = true)
    private String name;

    @Min(value = 1)
    @Column(nullable = false)
    private Integer population;

    @Size(min = 10)
    @Column(name = "travel_guide",columnDefinition = "TEXT",nullable = false)
    private String travelGuide;
}
