package softuni.productsshop.services.Product;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import softuni.productsshop.dtos.Product.ProductDTO;
import softuni.productsshop.dtos.Product.ProductInRangeNoBuyerDTO;
import softuni.productsshop.entities.Product;
import softuni.productsshop.repositories.ProductRepository;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static softuni.productsshop.constants.Paths.PRODUCTS_WITH_NO_BUYERS_IN_RANGE;
import static softuni.productsshop.constants.Utils.MODEL_MAPPER;
import static softuni.productsshop.constants.Utils.writeJsonIntoFile;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public List<ProductInRangeNoBuyerDTO> getAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(BigDecimal low, BigDecimal high) throws IOException {
        List<ProductInRangeNoBuyerDTO> products = productRepository.getAllByPriceBetweenAndBuyerIsNullOrderByPriceDesc(low, high)
                .orElseThrow(NoSuchElementException::new)
                .stream().map(product -> MODEL_MAPPER.map(product, ProductDTO.class))
                .map(ProductDTO::productInRangeNoBuyerDTO).collect(Collectors.toList());

        writeJsonIntoFile(products,PRODUCTS_WITH_NO_BUYERS_IN_RANGE);
        return products;

    }
}
