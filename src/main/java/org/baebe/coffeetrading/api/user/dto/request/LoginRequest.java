package org.baebe.coffeetrading.api.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.api.user.annotation.PasswordValid;
import org.baebe.coffeetrading.commons.types.user.AccountTypes;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "가입할 이메일이 입력되지 않았습니다. 이메일을 입력해주세요.")
    @Email(message = "지원하지 않는 형식의 이메일입니다.")
    private String email;

    @NotBlank(message = "패스워드가 입력되지 않았습니다. 패스워드를 입력해주세요.")
    @PasswordValid
    private String password;

    private AccountTypes accountType;
}
