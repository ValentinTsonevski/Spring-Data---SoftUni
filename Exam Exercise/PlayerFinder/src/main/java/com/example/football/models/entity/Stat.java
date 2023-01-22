package com.example.football.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "stats")
public class Stat extends BaseEntity{

    @Min(value = 1)
    @Column(nullable = false)
    private Float shooting;

    @Min(value = 1)
    @Column(nullable = false)
    private Float passing;

    @Min(value = 1)
    @Column(nullable = false)
    private Float endurance;
}
