package softuni.exam.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "agents")
public class Agent extends BaseEntity{

    @Size(min = 2)
    @Column(name = "first_name",unique = true,nullable = false)
    private String firstName;

    @Size(min = 2)
    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Email
    @Column(unique = true,nullable = false)
    private String email;

    @ManyToOne
    private Town town;

    public String getFullName(){
        return firstName + " " + lastName;
    }
}
