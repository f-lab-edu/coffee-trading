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
import org.baebe.coffeetrading.domains.store.entity.StoresEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "PRODUCTS")
public class ProductsEntity extends BaseCreatedAtEntity {

    @Column(name = "NAME", nullable = false)
    private String productName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORE_ID")
    private StoresEntity store;

    @Builder
    private ProductsEntity(
        String productName,
        String description,
        Integer price,
        StoresEntity store
    ) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.store = store;
    }
}
