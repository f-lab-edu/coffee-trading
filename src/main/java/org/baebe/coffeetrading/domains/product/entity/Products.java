package org.baebe.coffeetrading.domains.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "PRODUCTS")
public class Products extends ProductsEntity {

    @Builder
    private Products(
        String productName,
        String description,
        Integer price
    ) {
        this.setProductName(productName);
        this.setDescription(description);
        this.setPrice(price);
    }
}
