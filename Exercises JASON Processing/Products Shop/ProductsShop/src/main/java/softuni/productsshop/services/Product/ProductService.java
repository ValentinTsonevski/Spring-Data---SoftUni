package softuni.productsshop.services.Product;


import softuni.productsshop.dtos.Product.ProductInRangeNoBuyerDTO;
import softuni.productsshop.entities.Product;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;



public interface ProductService {
    List<ProductInRangeNoBuyerDTO> getAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(BigDecimal low, BigDecimal high) throws IOException;
}
