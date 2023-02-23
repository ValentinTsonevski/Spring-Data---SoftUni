package com.example.gamestore.domain.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table()
public class Orders extends BaseEntity{

    @OneToOne
    private User buyer;

    @ManyToMany
    private Set<Game> games;
}
