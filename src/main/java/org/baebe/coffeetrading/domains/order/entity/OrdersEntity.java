package org.baebe.coffeetrading.domains.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import org.baebe.coffeetrading.commons.types.order.OrderStatus;
import org.baebe.coffeetrading.commons.types.payment.PaymentTypes;
import org.baebe.coffeetrading.domains.common.BaseCreatedAtEntity;
import org.baebe.coffeetrading.domains.common.BaseTimeEntity;
import org.baebe.coffeetrading.domains.coupon.entity.Coupons;
import org.baebe.coffeetrading.domains.store.entity.Stores;
import org.baebe.coffeetrading.domains.user.entity.Users;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class OrdersEntity extends BaseTimeEntity {

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private Stores store;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private Coupons coupon;

    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private Integer totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAYMENT_TYPE", nullable = false)
    private PaymentTypes paymentType;
}
