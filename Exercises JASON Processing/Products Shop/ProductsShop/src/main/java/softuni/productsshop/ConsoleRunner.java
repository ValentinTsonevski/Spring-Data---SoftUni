package softuni.productsshop;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softuni.productsshop.services.Category.CategoryService;
import softuni.productsshop.services.Product.ProductService;
import softuni.productsshop.services.Seed.SeedService;
import softuni.productsshop.services.User.UserService;

import java.math.BigDecimal;

@Component
public class ConsoleRunner implements CommandLineRunner {
   private final SeedService seedService;
   private final ProductService productService;
   private final UserService userService;
   private final CategoryService categoryService;


    @Autowired
    public ConsoleRunner(SeedService seedService, ProductService productService, UserService userService, CategoryService categoryService) {
        this.seedService = seedService;
        this.productService = productService;

        this.userService = userService;
        this.categoryService = categoryService;
    }


    @Override
    public void run(String... args) throws Exception {
      //seedService.seedUsers();
      //seedService.seedCategories();
     // seedService.seedProducts();
        //this.productService.getAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(BigDecimal.valueOf(500),BigDecimal.valueOf(1000));
        this.userService.findAllByProductsSoldBuyerIsNotNullOrderByProductsSoldBuyerFirstName();

    }

}
