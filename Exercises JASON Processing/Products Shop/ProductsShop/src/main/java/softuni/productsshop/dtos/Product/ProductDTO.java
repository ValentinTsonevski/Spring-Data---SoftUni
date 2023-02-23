package softuni.productsshop.dtos.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import softuni.productsshop.dtos.Category.CategoryDTO;
import softuni.productsshop.dtos.User.UserDTO;
import softuni.productsshop.entities.User;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private String name;
    private BigDecimal price;
    private UserDTO seller;
    private Set<CategoryDTO> categories;

    private UserDTO buyer;


    public ProductInRangeNoBuyerDTO productInRangeNoBuyerDTO(){
        return new ProductInRangeNoBuyerDTO(name,price,seller.getFullName());
    }
}
