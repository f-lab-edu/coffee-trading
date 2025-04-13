package org.baebe.coffeetrading.commons.types.store;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StoreTypes {

    PRIVATE_STORE("개인"),
    FRANCHISE_STORE("프랜차이즈");

    private final String value;
}
