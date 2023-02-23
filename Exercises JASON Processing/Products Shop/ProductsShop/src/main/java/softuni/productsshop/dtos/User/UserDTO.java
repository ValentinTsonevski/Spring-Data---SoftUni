package softuni.productsshop.dtos.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.productsshop.entities.Product;
import softuni.productsshop.entities.User;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String firstName;
    private String lastName;
    private int age;
    private Set<Product> productsSold;
    private Set<Product> productsBought;
    private Set<User> friends;

    public String getFullName(){
        return firstName + " " + lastName;
    }
}
