package org.baebe.coffeetrading.api.user.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.baebe.coffeetrading.commons.types.user.AccountTypes;
import org.baebe.coffeetrading.commons.types.user.GenderTypes;
import org.baebe.coffeetrading.commons.types.user.UserStatus;
import org.baebe.coffeetrading.domains.user.entity.UsersEntity;

@Getter
public class RegisterResponse {

    /**
     * 필요에 따라 추가
     */

    private Long id;
    private String email;
    private String userName;
    private GenderTypes gender;
    private String nickname;
    private UserStatus status;
    private AccountTypes accountType;

    @Builder
    public RegisterResponse(
        Long id,
        String email,
        String userName,
        GenderTypes gender,
        String nickname,
        UserStatus status,
        AccountTypes accountType
    ) {
        this.id = id;
        this.email = email;
        this.userName = userName;
        this.gender = gender;
        this.nickname = nickname;
        this.status = status;
        this.accountType = accountType;
    }

    public static RegisterResponse of(UsersEntity user) {
        return RegisterResponse.builder()
            .id(user.getId())
            .email(user.getEmail())
            .userName(user.getUserName())
            .gender(user.getGender())
            .nickname(user.getNickname())
            .status(user.getStatus())
            .accountType(user.getAccountType())
            .build();
    }
}
