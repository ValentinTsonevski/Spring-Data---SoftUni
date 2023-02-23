package softuni.productsshop.services.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.productsshop.dtos.User.UsersWithSoldProductsDTO;
import softuni.productsshop.repositories.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static softuni.productsshop.constants.Paths.SOLD_PRODUCTS_WITH_BUYER;
import static softuni.productsshop.constants.Utils.MODEL_MAPPER;
import static softuni.productsshop.constants.Utils.writeJsonIntoFile;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UsersWithSoldProductsDTO> findAllByProductsSoldBuyerIsNotNullOrderByProductsSoldBuyerFirstName() throws IOException {
        List<UsersWithSoldProductsDTO> usersWithSoldProductsDTOS = this.userRepository
                .findAllBySoldProductsIsNotNullAndSoldProductsBuyerLastNameIsNotNullOrderBySoldProductsBuyerFirstName()
                .orElseThrow(NoSuchElementException::new)
                .stream().map(user -> MODEL_MAPPER.map(user, UsersWithSoldProductsDTO.class)).collect(Collectors.toList());


        writeJsonIntoFile(usersWithSoldProductsDTOS,SOLD_PRODUCTS_WITH_BUYER);

        return usersWithSoldProductsDTOS;
    }

}
