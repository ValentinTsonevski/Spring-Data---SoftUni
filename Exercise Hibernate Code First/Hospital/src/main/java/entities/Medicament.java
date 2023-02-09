package entities;

import javax.persistence.*;
import java.util.Set;

@Table
@Entity
public class Medicament {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "medicaments")
    private Set<Patient> patients;
}
