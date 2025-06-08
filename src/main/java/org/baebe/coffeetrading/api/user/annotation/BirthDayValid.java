package org.baebe.coffeetrading.api.user.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.baebe.coffeetrading.domains.user.validator.BirthDayValidator;

@Constraint(validatedBy = BirthDayValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDayValid {

    String message() default "생년월일 형식에 맞지 않게 입력되었습니다.";

    String regex() default "/^(19|20)\\d{2}-(0[1-9]|1[0-2])-([0-2][1-9]|3[01])$/";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
