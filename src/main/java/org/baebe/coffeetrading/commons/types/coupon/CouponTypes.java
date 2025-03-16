package org.baebe.coffeetrading.commons.types.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponTypes {

    FIXED_DISCOUNT("정액할인"),
    PERCENT_DISCOUNT("정률할인");

    private final String value;
}
