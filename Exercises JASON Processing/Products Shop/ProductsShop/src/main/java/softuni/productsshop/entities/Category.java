package softuni.productsshop.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.Objects;

import static softuni.productsshop.constants.ExceptionsMessages.CATEGORY_NAME_CONSTRAINT;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category extends BaseEntity{
    @Column
    private String name;

    public Category(String name) {
        setName(name);
    }

    public void setName(String name) {
        if(name.length() >= 3 && name.length() <= 15){
            this.name = name;
        }else{
            throw new IllegalArgumentException(CATEGORY_NAME_CONSTRAINT);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name)
                && Objects.equals(getId(),category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name,getId());
    }
}
