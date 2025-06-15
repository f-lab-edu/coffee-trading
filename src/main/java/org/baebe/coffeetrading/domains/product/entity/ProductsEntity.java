package org.baebe.coffeetrading.domains.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.commons.types.product.ProductStatus;
import org.baebe.coffeetrading.commons.types.product.ProductType;
import org.baebe.coffeetrading.domains.common.BaseTimeEntity;
import org.baebe.coffeetrading.domains.store.entity.StoresEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "PRODUCTS")
public class ProductsEntity extends BaseTimeEntity {

    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    @Column(name = "PRICE", nullable = false)
    private Integer price;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private ProductType type;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORE_ID")
    private StoresEntity store;

    @Builder
    public ProductsEntity(
        String name,
        String description,
        Integer price,
        ProductType type,
        ProductStatus status,
        StoresEntity store
    ) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.status = status;
        this.store = store;
    }

    public void update(String name, String description, Integer price, ProductType type, ProductStatus status) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.status = status;
    }
}
