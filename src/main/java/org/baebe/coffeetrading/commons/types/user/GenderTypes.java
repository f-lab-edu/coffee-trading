package org.baebe.coffeetrading.commons.types.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GenderTypes {
    MAN("남자"),
    WOMAN("여자");

    private final String value;
}
