package org.baebe.coffeetrading.domains.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.domains.product.entity.Products;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ORDER_ITEMS")
public class OrderItems extends OrderItemsEntity{

    private OrderItems(
        Integer count,
        Orders order,
        Products product
    ) {
        this.setCount(count);
        this.setOrder(order);
        this.setProduct(product);
    }
}
