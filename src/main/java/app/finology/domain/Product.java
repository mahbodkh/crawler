package app.finology.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.beans.Transient;
import java.util.Objects;

/**
 * Created by Ebrahim Kh.
 */

@Entity
@Table(name = "\"product\"")
@NoArgsConstructor
@Data
@ToString(exclude = "detail")
@Builder
public class Product {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", length = 50)
    private String name;
    private Double price;
    @Column(name = "description", length = 300)
    private String description;
    @Column(name = "detail", length = 100)
    private String detail;

    @Builder(toBuilder = true)
    public Product(Long id, String name, Double price, String description, String detail) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.detail = detail;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }


    @Transient
    public static Product getBasicProduct(String name, Double price, String description, String detail) {
        return Product.builder()
            .name(name)
            .price(price)
            .description(description)
            .detail(detail)
            .build();
    }

}
