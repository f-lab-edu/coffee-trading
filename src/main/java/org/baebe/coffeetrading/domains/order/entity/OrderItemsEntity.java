package org.baebe.coffeetrading.domains.order.entity;

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
import org.baebe.coffeetrading.domains.product.entity.ProductsEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ORDER_ITEMS")
public class OrderItemsEntity extends BaseCreatedAtEntity {

    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private OrdersEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private ProductsEntity product;

    @Builder
    private OrderItemsEntity(
        Integer count,
        OrdersEntity order,
        ProductsEntity product
    ) {
        this.count = count;
        this.order = order;
        this.product = product;
    }
}
