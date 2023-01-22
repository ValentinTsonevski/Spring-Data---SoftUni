package com.example.football.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "teams")
public class Team extends BaseEntity{

    @Size(min = 3)
    @Column(nullable = false,unique = true)
    private String name;

    @Size(min = 3)
    @Column(name = "stadium_name",nullable = false)
    private String stadiumName;

    @Min(value = 1000)
    @Column(name = "fan_base",nullable = false)
    private Integer fanBase;

    @Size(min = 10)
    @Column(columnDefinition = "TEXT",nullable = false)
    private String history;

    @ManyToOne
    private Town town;
}
