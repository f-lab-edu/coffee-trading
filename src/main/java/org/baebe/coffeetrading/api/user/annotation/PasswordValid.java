package org.baebe.coffeetrading.api.user.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.baebe.coffeetrading.domains.user.validator.PasswordValidator;

@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordValid {
    String message() default "비밀번호는 8자 이상 영문 대,소문자와 숫자, 특수기호가 적어도 1개 이상씩 포함되어야 합니다.";

    String regex() default "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).+";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
