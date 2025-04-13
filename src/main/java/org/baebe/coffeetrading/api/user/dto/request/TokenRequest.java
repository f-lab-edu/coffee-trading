package org.baebe.coffeetrading.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRequest {

    @NotBlank(message = "refreshToken은 필수 값입니다.")
    private String refreshToken;
}
