package softuni.productsshop.dtos.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.productsshop.dtos.Product.SoldProductDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersWithSoldProductsDTO {
    private String firstName;

    private String lastName;

    private List<SoldProductDTO> soldProducts;
}
