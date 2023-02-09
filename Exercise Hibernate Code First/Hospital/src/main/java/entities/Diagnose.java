package entities;

import javax.persistence.*;
import java.util.Set;

@Table
@Entity
public class Diagnose {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column
    private String comments;

    @ManyToMany(mappedBy = "diagnoses")
    private Set<Patient> patients;

}
