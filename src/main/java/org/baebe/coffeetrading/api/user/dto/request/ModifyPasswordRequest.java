package org.baebe.coffeetrading.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.baebe.coffeetrading.api.user.annotation.PasswordValid;

@Getter
@AllArgsConstructor
public class ModifyPasswordRequest {

    @NotBlank(message = "변경 전 패스워드가 입력되지 않았습니다. 패스워드를 입력해주세요.")
    private String oldPassword;

    @NotBlank(message = "변경 할 패스워드가 입력되지 않았습니다. 패스워드를 입력해주세요.")
    @PasswordValid
    private String newPassword;
}
