package org.baebe.coffeetrading.commons.types.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResultTypes {
    SUCCESS("성공"),
    FAIL("실패");

    private final String value;
}
