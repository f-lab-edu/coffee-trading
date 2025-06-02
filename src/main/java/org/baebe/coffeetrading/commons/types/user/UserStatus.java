package org.baebe.coffeetrading.commons.types.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserStatus {
    ENABLED("정상"),
    DORMANT("휴면"),
    DISABLED("탈퇴");

    private final String value;
}
