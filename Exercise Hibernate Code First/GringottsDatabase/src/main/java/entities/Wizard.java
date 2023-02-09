package entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "wizard")
public class Wizard {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", length = 50)
    private String fistName;

    @Column(name = "last_name", length = 60, nullable = false)
    private String lastName;

    @Column(length = 1000)
    private String notes;

    @Column(nullable = false)
    private int age;

    @OneToOne
    @JoinColumn
    private MagicWand magicWand;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "wizard_deposit")
    private List<Deposits> deposits;

    public Wizard() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public MagicWand getMagicWand() {
        return magicWand;
    }

    public void setMagicWand(MagicWand magicWand) {
        this.magicWand = magicWand;
    }

    public List<Deposits> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<Deposits> deposits) {
        this.deposits = deposits;
    }
}
