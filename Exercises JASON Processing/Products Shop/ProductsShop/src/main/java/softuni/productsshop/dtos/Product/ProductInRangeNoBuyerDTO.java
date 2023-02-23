package softuni.productsshop.dtos.Product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductInRangeNoBuyerDTO {
    private String name;
    private BigDecimal price;
    private String seller;
}
