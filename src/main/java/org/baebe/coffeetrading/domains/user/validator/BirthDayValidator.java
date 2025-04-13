package org.baebe.coffeetrading.domains.user.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import org.baebe.coffeetrading.api.user.annotation.BirthDayValid;

public class BirthDayValidator implements ConstraintValidator<BirthDayValid, String> {

    private String regexp;

    @Override
    public void initialize(BirthDayValid constraintAnnotation) {
        this.regexp = constraintAnnotation.regex();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return Pattern.matches(regexp, value);
    }
}
