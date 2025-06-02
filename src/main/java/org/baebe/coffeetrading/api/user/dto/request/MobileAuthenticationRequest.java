package org.baebe.coffeetrading.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.baebe.coffeetrading.api.user.annotation.BirthDayValid;
import org.baebe.coffeetrading.api.user.annotation.PhoneNumberValid;
import org.baebe.coffeetrading.commons.types.user.GenderTypes;

@Getter
@AllArgsConstructor
public class MobileAuthenticationRequest {

    @NotBlank(message = "이름이 입력되지 않았습니다. 이름을 입력해 주세요.")
    private String userName;

    @NotBlank(message = "전화번호가 입력되지 않았습니다. 전화번호를 입력해 주세요.")
    @PhoneNumberValid
    private String phone;

    @NotBlank(message = "생년월일이 입력되지 않았습니다. 생년월일을 입력해 주세요.")
    @BirthDayValid
    private String birthday;

    @NotBlank(message = "성별을 선택해 주세요.")
    private GenderTypes gender;
}
