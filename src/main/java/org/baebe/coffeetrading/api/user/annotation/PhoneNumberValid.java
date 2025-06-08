package org.baebe.coffeetrading.api.user.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.baebe.coffeetrading.domains.user.validator.PhoneNumberValidator;

@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumberValid {
    String message() default "전화번호 형식에 맞지 않게 입력되었습니다.";

    String regexp() default "^\\d{2,3}-\\d{3,4}-\\d{4}$";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
