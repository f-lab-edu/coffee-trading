package org.baebe.coffeetrading.commons.types.product;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {

    FOR_SALE("판매중"),
    SOLD_OUT("품절");

    private final String value;
}
