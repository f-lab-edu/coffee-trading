package org.baebe.coffeetrading.api.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import org.baebe.coffeetrading.api.user.annotation.PasswordValid;
import org.baebe.coffeetrading.commons.types.user.AccountTypes;
import org.baebe.coffeetrading.commons.types.user.UserRole;

@Getter
public class RegisterRequest {

    @NotBlank(message = "가입할 이메일이 입력되지 않았습니다. 이메일을 입력해주세요.")
    @Email(message = "지원하지 않는 형식의 이메일입니다.")
    private String email;

    @NotBlank(message = "패스워드가 입력되지 않았습니다. 패스워드를 입력해주세요.")
    @PasswordValid
    private String password;

    @NotBlank(message = "닉네임이 입력되지 않았습니다. 닉네임을 입력해 주세요.")
    private String nickname;

    private AccountTypes accountType;

    private UserRole userType;

    @Builder
    public RegisterRequest(String email, String password, String nickname, AccountTypes accountType,
        UserRole userType) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.accountType = accountType;
        this.userType = userType;
    }
}
