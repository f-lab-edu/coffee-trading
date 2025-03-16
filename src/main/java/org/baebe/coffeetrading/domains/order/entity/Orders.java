package org.baebe.coffeetrading.domains.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.commons.types.order.OrderStatus;
import org.baebe.coffeetrading.commons.types.payment.PaymentTypes;
import org.baebe.coffeetrading.domains.coupon.entity.Coupons;
import org.baebe.coffeetrading.domains.store.entity.Stores;
import org.baebe.coffeetrading.domains.user.entity.Users;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ORDERS")
public class Orders extends OrdersEntity {

    private Orders(
        OrderStatus orderStatus,
        Users user,
        Stores store,
        Coupons coupon,
        Integer totalAmount,
        PaymentTypes paymentType
    ) {
        this.setOrderStatus(orderStatus);
        this.setUser(user);
        this.setStore(store);
        this.setCoupon(coupon);
        this.setTotalAmount(totalAmount);
        this.setPaymentType(paymentType);
    }
}
