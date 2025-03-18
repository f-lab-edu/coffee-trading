package org.baebe.coffeetrading.domains.coupon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.commons.types.coupon.CouponStatus;
import org.baebe.coffeetrading.commons.types.coupon.CouponTypes;
import org.baebe.coffeetrading.domains.common.BaseTimeEntity;
import org.baebe.coffeetrading.domains.order.entity.OrdersEntity;
import org.baebe.coffeetrading.domains.user.entity.UsersEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "COUPONS")
public class CouponsEntity extends BaseTimeEntity {

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private CouponTypes couponType;

    @Column(name = "PRICE")
    private Integer price;

    @Column(name = "DISCOUNT_LIMIT")
    private Integer discountLimit;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private CouponStatus couponStatus;

    @Column(name = "EXPIRY_DATE")
    private LocalDate expiryDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    private OrdersEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UsersEntity user;

    @Builder
    private CouponsEntity(
        String name,
        String description,
        CouponTypes couponType,
        Integer price,
        Integer discountLimit,
        CouponStatus couponStatus,
        LocalDate expiryDate,
        OrdersEntity order,
        UsersEntity user
    ) {
        this.name = name;
        this.description = description;
        this.couponType = couponType;
        this.price = price;
        this.discountLimit = discountLimit;
        this.couponStatus = couponStatus;
        this.expiryDate = expiryDate;
        this.order = order;
        this.user = user;
    }
}
