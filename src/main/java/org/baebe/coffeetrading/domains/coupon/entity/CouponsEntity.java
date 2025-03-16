package org.baebe.coffeetrading.domains.coupon.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.baebe.coffeetrading.commons.types.coupon.CouponStatus;
import org.baebe.coffeetrading.commons.types.coupon.CouponTypes;
import org.baebe.coffeetrading.domains.common.BaseCreatedAtEntity;
import org.baebe.coffeetrading.domains.order.entity.Orders;
import org.baebe.coffeetrading.domains.user.entity.Users;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class CouponsEntity extends BaseCreatedAtEntity {

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
    @JoinColumn(name = "ID")
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private Users user;

}
