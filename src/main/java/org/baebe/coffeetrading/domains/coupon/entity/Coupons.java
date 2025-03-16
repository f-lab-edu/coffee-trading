package org.baebe.coffeetrading.domains.coupon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.commons.types.coupon.CouponStatus;
import org.baebe.coffeetrading.commons.types.coupon.CouponTypes;
import org.baebe.coffeetrading.domains.order.entity.Orders;
import org.baebe.coffeetrading.domains.user.entity.Users;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "COUPONS")
public class Coupons extends CouponsEntity{

    private Coupons (
        String name,
        String description,
        CouponTypes couponType,
        Integer price,
        Integer discountLimit,
        CouponStatus couponStatus,
        LocalDate expiryDate,
        Orders order,
        Users user
    ) {
        this.setName(name);
        this.setDescription(description);
        this.setCouponType(couponType);
        this.setPrice(price);
        this.setDiscountLimit(discountLimit);
        this.setCouponStatus(couponStatus);
        this.setExpiryDate(expiryDate);
        this.setOrder(order);
        this.setUser(user);
    }
}