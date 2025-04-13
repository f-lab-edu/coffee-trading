package org.baebe.coffeetrading.domains.user.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
import org.baebe.coffeetrading.api.user.annotation.PhoneNumberValid;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberValid, String> {

    private String regexp;

    @Override
    public void initialize(PhoneNumberValid constraintAnnotation) {
        this.regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return Pattern.matches(regexp, value);
    }
}
