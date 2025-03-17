package org.baebe.coffeetrading.domains.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.commons.types.order.OrderStatus;
import org.baebe.coffeetrading.commons.types.payment.PaymentTypes;
import org.baebe.coffeetrading.domains.common.BaseTimeEntity;
import org.baebe.coffeetrading.domains.coupon.entity.CouponsEntity;
import org.baebe.coffeetrading.domains.store.entity.StoresEntity;
import org.baebe.coffeetrading.domains.user.entity.UsersEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "ORDERS")
public class OrdersEntity extends BaseTimeEntity {

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UsersEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORE_ID")
    private StoresEntity store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private CouponsEntity coupon;

    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private Integer totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAYMENT_TYPE", nullable = false)
    private PaymentTypes paymentType;

    @Builder
    private OrdersEntity(
        OrderStatus orderStatus,
        UsersEntity user,
        StoresEntity store,
        CouponsEntity coupon,
        Integer totalAmount,
        PaymentTypes paymentType
    ) {
        this.orderStatus = orderStatus;
        this.user = user;
        this.store = store;
        this.coupon = coupon;
        this.totalAmount = totalAmount;
        this.paymentType = paymentType;
    }
}
