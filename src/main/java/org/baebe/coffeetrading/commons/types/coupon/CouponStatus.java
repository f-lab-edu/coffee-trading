package org.baebe.coffeetrading.commons.types.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponStatus {

    USE("사용가능"),
    DEL("사용불가");

    private final String value;
}
