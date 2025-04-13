package org.baebe.coffeetrading.api.user.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
@AllArgsConstructor
public class ErrorFieldDto {
    private final String fieldName;
    private final String errorMessage;

    public static ErrorFieldDto of(FieldError error) {
        return new ErrorFieldDto(error.getField(), error.getDefaultMessage());
    }

    public static ErrorFieldDto of(String fieldName, String errMessage) {
        if (errMessage == null) {
            errMessage = "";
        }
        return new ErrorFieldDto(fieldName, errMessage);
    }
}
