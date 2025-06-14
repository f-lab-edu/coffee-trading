package org.baebe.coffeetrading.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.baebe.coffeetrading.api.user.annotation.PasswordValid;

@Getter
public class ModifyPasswordRequest {

    @NotBlank(message = "변경 전 패스워드가 입력되지 않았습니다. 패스워드를 입력해주세요.")
    private String oldPassword;

    @NotBlank(message = "변경 할 패스워드가 입력되지 않았습니다. 패스워드를 입력해주세요.")
    @PasswordValid
    @Setter
    private String newPassword;

    @Builder
    public ModifyPasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
