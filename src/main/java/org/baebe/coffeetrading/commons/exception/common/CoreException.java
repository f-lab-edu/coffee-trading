package org.baebe.coffeetrading.commons.exception.common;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;

@Slf4j
@Getter
public class CoreException extends RuntimeException {

    private final ErrorTypes errorTypes;
    private final Object data;

    public CoreException(ErrorTypes errorTypes) {
        super(errorTypes.getErrorMessage());
        this.errorTypes = errorTypes;
        this.data = null;
    }

    public CoreException(ErrorTypes errorTypes, Object data) {
        super(errorTypes.getErrorMessage());
        this.errorTypes = errorTypes;
        this.data = data;
    }

    public CoreException(ErrorTypes errorTypes, String message) {
        this(errorTypes, null, message);
    }

    public CoreException(ErrorTypes errorTypes, Object data, String message) {
        super(message);
        this.errorTypes = errorTypes;
        this.data = data;
    }

    public CoreException(ErrorTypes errorTypes, Object data, Throwable cause) {
        super(errorTypes.getErrorMessage(), cause);
        this.errorTypes = errorTypes;
        this.data = null;
    }

    public CoreException(ErrorTypes errorTypes, Object data, String message, Throwable cause) {
        super(message, cause);
        this.errorTypes = errorTypes;
        this.data = data;
    }
}
