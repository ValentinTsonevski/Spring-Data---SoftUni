package softuni.productsshop.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

import static softuni.productsshop.constants.ExceptionsMessages.PRODUCT_NAME_MIN_LENGTH;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private User buyer;

    @ManyToOne
    @Fetch(FetchMode.JOIN)
    private User seller;


    @ManyToMany
    @Fetch(FetchMode.JOIN)
    private Set<Category> categories;



    public Product(String name, BigDecimal price, User buyer, User seller, Set<Category> categories) {
        setName(name);
        this.price = price;
        this.buyer = buyer;
        this.seller = seller;
        this.categories = categories;
    }

    public void setName(String name) {
        if(name.length() < 3){
            throw new IllegalArgumentException(PRODUCT_NAME_MIN_LENGTH);
        }
        this.name = name;
    }
}
