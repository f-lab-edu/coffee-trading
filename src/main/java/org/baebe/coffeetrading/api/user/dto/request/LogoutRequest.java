package org.baebe.coffeetrading.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LogoutRequest {

    @NotBlank(message = "사용자 정보가 확인되지 않습니다.")
    String userId;
    @NotBlank(message = "액세스 토큰 정보가 확인되지 않습니다.")
    String accessToken;
    @NotBlank(message = "리프레쉬 토큰 정보가 확인되지 않습니다.")
    String refreshToken;
}
