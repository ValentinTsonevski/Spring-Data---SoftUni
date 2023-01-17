package softuni.exam.models.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mechanics")
public class Mechanic extends BaseEntity{

    @Size(min = 2)
    @Column(name = "first_name",unique = true,nullable = false)
    private String firstName;

    @Size(min = 2)
    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Email
    @Column(unique = true)
    private String email;

    @Size(min = 2)
    @Column(unique = true)
    private String phone;
}
