package entities;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private int quantity;

    @Column
    private double price;


    @OneToMany(mappedBy = "product")
    private Set<Sale> productSaleSet;


    public Product() {
    }

    public Set<Sale> getProductSaleSet() {
        return productSaleSet;
    }

    public void setProductSaleSet(Set<Sale> productSaleSet) {
        this.productSaleSet = productSaleSet;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
