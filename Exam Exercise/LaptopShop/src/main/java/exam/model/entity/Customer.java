package exam.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity{

    @Size(min = 2)
    @Column(name = "first_name",nullable = false)
    private String firstName;

    @Size(min = 2)
    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Email
    @Column(unique = true)
    private String email;

    @Column(name = "registered_on",nullable = false)
    private LocalDate registeredOn;

    @ManyToOne
    private Town town;
}
