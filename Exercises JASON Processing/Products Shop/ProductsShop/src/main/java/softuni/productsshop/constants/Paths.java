package softuni.productsshop.constants;

import java.nio.file.Path;

public class Paths {
    ;
    public static final Path USER_JSON_PATH = Path.of("src/main/resources/09. DB-Advanced-JSON-Processing-Exercises/users.json");
    public static final Path PRODUCT_JSON_PATH = Path.of("src/main/resources/09. DB-Advanced-JSON-Processing-Exercises/products.json");
    public static final Path CATEGORIES_JSON_PATH = Path.of("src/main/resources/09. DB-Advanced-JSON-Processing-Exercises/categories.json");
    public static final Path PRODUCTS_WITH_NO_BUYERS_IN_RANGE = Path.of("src/main/resources/outputs/products-in-range.json");

    public static final Path SOLD_PRODUCTS_WITH_BUYER = Path.of("src/main/resources/outputs/users-sold-products.json");
}
