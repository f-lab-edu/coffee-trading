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
@Table(name = "PRODUCT_OPTIONS")
public class ProductOptions extends ProductOptionsEntity{

    @Builder
    private ProductOptions(
        String productOptionName,
        String description,
        Integer optionPrice,
        Products product
    ) {
        this.setProductOptionName(productOptionName);
        this.setDescription(description);
        this.setOptionPrice(optionPrice);
        this.setProduct(product);
    }
}
