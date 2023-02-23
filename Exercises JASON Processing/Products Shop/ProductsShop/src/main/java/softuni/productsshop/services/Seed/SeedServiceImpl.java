package softuni.productsshop.services.Seed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.productsshop.dtos.Category.ImportCategoryDTO;
import softuni.productsshop.dtos.Product.ImportProductDTO;
import softuni.productsshop.dtos.User.ImportUserDTO;
import softuni.productsshop.entities.Category;
import softuni.productsshop.entities.Product;
import softuni.productsshop.entities.User;
import softuni.productsshop.repositories.CategoryRepository;
import softuni.productsshop.repositories.ProductRepository;
import softuni.productsshop.repositories.UserRepository;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


import static softuni.productsshop.constants.Paths.*;
import static softuni.productsshop.constants.Utils.GSON;
import static softuni.productsshop.constants.Utils.MODEL_MAPPER;

@Service
public class SeedServiceImpl implements SeedService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;


    @Autowired
    public SeedServiceImpl(UserRepository userRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public void seedUsers() throws IOException {
        if (userRepository.count() == 0) {
            FileReader fileReader = new FileReader(USER_JSON_PATH.toFile());
            List<User> users = Arrays.stream(GSON.fromJson(fileReader, ImportUserDTO[].class))
                    .map(importUserDTO -> MODEL_MAPPER.map(importUserDTO, User.class)).toList();

            userRepository.saveAllAndFlush(users);
            fileReader.close();
        }

    }

    @Override
    public void seedCategories() throws IOException {
        if(productRepository.count() == 0){
            FileReader fileReader = new FileReader(CATEGORIES_JSON_PATH.toFile());
            List<Category> categories = Arrays.stream(GSON.fromJson(fileReader, ImportCategoryDTO[].class))
                    .map(importCategoryDTO -> MODEL_MAPPER.map(importCategoryDTO, Category.class)).toList();

            this.categoryRepository.saveAllAndFlush(categories);
            fileReader.close();
        }
    }

    @Override
    public void seedProducts() throws IOException {
        if (productRepository.count() == 0) {
            FileReader fileReader = new FileReader(PRODUCT_JSON_PATH.toFile());
            List<Product> products = Arrays.stream(GSON.fromJson(fileReader, ImportProductDTO[].class))
                    .map(importProductDTO -> MODEL_MAPPER.map(importProductDTO, Product.class))
                    .map(this::setRandomSeller)
                    .map(this::setRandomBuyer)
                    .map(this::setRandomCategory)
                    .toList();

            this.productRepository.saveAllAndFlush(products);
            fileReader.close();
        }
    }

    private Product setRandomBuyer(Product product) {
        if(product.getPrice().compareTo(BigDecimal.valueOf(700L)) > 0){
        User buyer = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);

        while (buyer.equals(product.getSeller())) {
            buyer = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);
        }
        product.setSeller(buyer);
        }

        return product;
    }

    private Product setRandomSeller(Product product) {
        User seller = this.userRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);
        product.setSeller(seller);
        return product;
    }

    private Product setRandomCategory(Product product) {

        final Random random = new Random();

        final int numberOfCategories = random.nextInt((int) this.categoryRepository.count());

        Set<Category> categories = new HashSet<>();

        for (int i = 0; i < numberOfCategories; i++) {
            Category category = this.categoryRepository.getRandomEntity().orElseThrow(IllegalArgumentException::new);
            categories.add(category);
        }

        product.setCategories(categories);
        return product;
    }


}
