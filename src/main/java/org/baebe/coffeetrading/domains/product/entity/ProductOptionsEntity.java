package org.baebe.coffeetrading.domains.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.domains.common.BaseCreatedAtEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "PRODUCT_OPTIONS")
public class ProductOptionsEntity extends BaseCreatedAtEntity {

    @Column(name = "NAME", nullable = false)
    private String productOptionName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private Integer optionPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private ProductsEntity product;

    @Builder
    private ProductOptionsEntity(
        String productOptionName,
        String description,
        Integer optionPrice,
        ProductsEntity product
    ) {
        this.productOptionName = productOptionName;
        this.description = description;
        this.optionPrice = optionPrice;
        this.product = product;
    }
}
