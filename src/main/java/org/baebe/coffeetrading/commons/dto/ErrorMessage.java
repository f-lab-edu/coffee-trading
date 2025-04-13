package org.baebe.coffeetrading.commons.dto;

import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;

public record ErrorMessage(
    String message,
    Object data
) {
    public ErrorMessage(ErrorTypes errorType) {
            this(errorType.getErrorMessage(), null);
    }

    public ErrorMessage(ErrorTypes errorType, Object data) {
            this(errorType.getErrorMessage(), data);
    }

    public ErrorMessage(CoreException e) {
            this(e.getErrorTypes().getErrorMessage(), null);
    }

    public ErrorMessage(CoreException e, Object data) {
            this(e.getErrorTypes().getErrorMessage(), data);
    }
}
