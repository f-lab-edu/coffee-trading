package org.baebe.coffeetrading.api.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.commons.dto.response.ApiResponse;
import org.baebe.coffeetrading.commons.dto.vo.ErrorFieldDto;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionRestResponseAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleBindException(MethodArgumentNotValidException e) {
        log.error("Bind Exception : {}", e.getMessage(), e);
        List<ErrorFieldDto> errorList = e.getBindingResult().getFieldErrors()
            .stream()
            .map(ErrorFieldDto::of)
            .toList();

        return ApiResponse.error(ErrorTypes.BAD_ILLEAGAL_ARGUMENT, errorList);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ApiResponse<?> missingResultValueException(HandlerMethodValidationException e) {
        log.error("HandlerMethodValidation Exception : {}", e.getMessage(), e);

        List<ErrorFieldDto> errorList = e.getParameterValidationResults()
            .stream()
            .map(err -> {
                String errMessage = err.getResolvableErrors()
                    .stream()
                    .findFirst()
                    .map(MessageSourceResolvable::getDefaultMessage)
                    .orElse("");

                return ErrorFieldDto.of(err.getMethodParameter().getParameterName(), errMessage);
            }).toList();
        return ApiResponse.error(ErrorTypes.BAD_ILLEAGAL_ARGUMENT, errorList);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleException(Exception e) {
        log.error("Exception >>> {}", e.getMessage(), e);
        if (e instanceof CoreException coreException) {
            return ApiResponse.error(
                coreException.getErrorTypes(),
                coreException.getData(),
                coreException
            );
        }
        return ApiResponse.error(ErrorTypes.UNSPECIFIED);
    }

    @ExceptionHandler(CoreException.class)
    public ApiResponse<?> handleCoreException(CoreException e) {
        switch (e.getErrorTypes().getLogLevel()) {
            case ERROR -> log.error("[ErrType] >>> {}, [ErrMessage] >>> {}", e.getErrorTypes(), e.getMessage(), e);
            case WARN -> log.warn("[ErrType] >>> {}, [ErrMessage] >>> {}", e.getErrorTypes(), e.getMessage(), e);
            default -> log.info("[ErrType] >>> {}, [ErrMessage] >>> {}", e.getErrorTypes(), e.getMessage(), e);
        }
        return ApiResponse.error(e.getErrorTypes(), e.getData(), e);
    }
}
