package org.baebe.coffeetrading.domains.user.template;

import static org.baebe.coffeetrading.commons.types.user.UserStatus.*;

import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.commons.types.user.AccountTypes;
import org.baebe.coffeetrading.commons.types.user.UserRole;
import org.baebe.coffeetrading.domains.user.entity.UsersEntity;

@Slf4j
public class UserTemplates {

    public static UsersEntity ofUser(
        String email,
        String password,
        String nickname,
        AccountTypes accountType,
        UserRole userType
        ) {
        return of(email, password, nickname, accountType, userType);
    }

    private static UsersEntity of(
        String email,
        String password,
        String nickname,
        AccountTypes accountType,
        UserRole userType
    ) {
        log.info("[UserTemplates.of()] email >>> {}, UserType >>> {}", email, userType);
        return UsersEntity.builder()
            .email(email)
            .password(password)
            .userName(null)
            .phone(null)
            .birthDay(null)
            .gender(null)
            .nickname(nickname)
            .status(ENABLED)
            .accountType(accountType)
            .userType(userType)
            .build();
    }
}
