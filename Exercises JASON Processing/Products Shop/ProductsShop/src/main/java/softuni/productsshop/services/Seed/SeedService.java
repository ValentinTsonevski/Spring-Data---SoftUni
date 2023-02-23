package softuni.productsshop.services.Seed;


import java.io.FileNotFoundException;
import java.io.IOException;

public interface SeedService {
    void seedUsers() throws IOException;
    void seedProducts() throws IOException;
    void seedCategories() throws IOException;
}
