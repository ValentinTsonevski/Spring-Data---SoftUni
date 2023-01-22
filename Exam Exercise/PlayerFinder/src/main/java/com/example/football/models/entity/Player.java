package com.example.football.models.entity;

import com.example.football.enums.Position;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "players")
public class Player extends BaseEntity{

    @Size(min = 3)
    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Size(min = 3)
    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Email
    @Column(nullable = false,unique = true)
    private String email;

    @Column(name = "birth_date",nullable = false)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Position position;

    @ManyToOne
    private Town town;

    @ManyToOne
    private Team team;

    @ManyToOne
    private Stat stat;

}
