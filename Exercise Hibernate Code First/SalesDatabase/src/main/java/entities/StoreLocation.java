package entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "store_location")
public class StoreLocation {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

    @Column
    private String locationName;

    @OneToMany(mappedBy = "storeLocation")
    private Set<Sale> storeLocationSaleSet;

    public StoreLocation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Set<Sale> getStoreLocationSaleSet() {
        return storeLocationSaleSet;
    }

    public void setStoreLocationSaleSet(Set<Sale> storeLocationSaleSet) {
        this.storeLocationSaleSet = storeLocationSaleSet;
    }
}
