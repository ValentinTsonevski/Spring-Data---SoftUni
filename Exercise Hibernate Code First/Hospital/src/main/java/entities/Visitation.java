package entities;

import javax.persistence.*;
import java.util.Date;

@Table
@Entity
public class Visitation {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Date date;

    @Column
    private String comments;

    @ManyToOne(optional = false)
    @JoinColumn(referencedColumnName = "id")
    private Patient patient;
}
