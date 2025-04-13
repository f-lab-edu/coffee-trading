package org.baebe.coffeetrading.api.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.baebe.coffeetrading.commons.types.user.GenderTypes;
import org.baebe.coffeetrading.domains.user.entity.UsersEntity;

@Getter
public class LoginResponse {

    private final String username;
    private final GenderTypes gender;
    private final String birthDay;
    private final String accessToken;
    private final String refreshToken;

    @Builder
    private LoginResponse(
        String username,
        GenderTypes gender,
        String birthDay,
        String accessToken,
        String refreshToken
    ) {
        this.username = username;
        this.gender = gender;
        this.birthDay = birthDay;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    public static LoginResponse of(String accessToken, String refreshToken, UsersEntity user) {
        return LoginResponse.builder()
            .username(user.getUserName())
            .gender(user.getGender())
            .birthDay(user.getBirthDay())
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .build();
    }
}
