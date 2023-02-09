package entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Month;
import java.time.Year;

@Entity
@Table
public class CreditCard extends BillingDetail {

    @Column(nullable = false)
    private String type;

    @Column(name = "expiration_month",nullable = false)
    private Month expirationMonth;

    @Column(name = "expiration_year",nullable = false)
    private Year expirationYear;


    public CreditCard() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Month getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(Month expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public Year getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(Year expirationYear) {
        this.expirationYear = expirationYear;
    }
}
