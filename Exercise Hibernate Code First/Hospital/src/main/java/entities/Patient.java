package entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table
public class Patient {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String address;

    @Column
    private String email;

    @Column(name = "date_of_birth",nullable = false)
    private Date dateOfBirth;

    @Column
    private String picture;

    @Column(name = "medical_insurance")
    private Boolean medicalInsurance;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
    private Set<Diagnose> diagnoses;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(referencedColumnName = "id"))
    private Set<Medicament> medicaments;

    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL)
    private Set<Visitation> visitations;
}
