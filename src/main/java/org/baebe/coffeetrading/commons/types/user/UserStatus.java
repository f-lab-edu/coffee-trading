package org.baebe.coffeetrading.commons.types.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    ENABLED("활성화"),
    DORMANT("휴면"),
    DISABLED("비활성화");

    private final String value;
}
