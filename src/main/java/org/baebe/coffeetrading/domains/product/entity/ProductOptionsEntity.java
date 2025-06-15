package org.baebe.coffeetrading.domains.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.commons.types.product.ProductStatus;
import org.baebe.coffeetrading.domains.common.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "PRODUCT_OPTIONS")
public class ProductOptionsEntity extends BaseTimeEntity {

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private Integer price;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private ProductsEntity product;

    @Builder
    private ProductOptionsEntity(
        String name,
        String description,
        Integer price,
        ProductStatus status,
        ProductsEntity product
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.product = product;
    }

    public void update(
        String name,
        String description,
        Integer price,
        ProductStatus status
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
    }
}
