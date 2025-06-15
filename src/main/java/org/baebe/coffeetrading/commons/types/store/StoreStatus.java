package org.baebe.coffeetrading.commons.types.store;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreStatus {

    OPEN("영업중"),
    CLOSE("영업종료");

    private final String value;
}
