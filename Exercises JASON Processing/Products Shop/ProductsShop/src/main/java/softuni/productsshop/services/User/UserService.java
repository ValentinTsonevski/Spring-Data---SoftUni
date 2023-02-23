package softuni.productsshop.services.User;

import softuni.productsshop.dtos.User.UsersWithSoldProductsDTO;

import java.io.IOException;
import java.util.List;

public interface UserService {
    List<UsersWithSoldProductsDTO> findAllByProductsSoldBuyerIsNotNullOrderByProductsSoldBuyerFirstName() throws IOException;

}
