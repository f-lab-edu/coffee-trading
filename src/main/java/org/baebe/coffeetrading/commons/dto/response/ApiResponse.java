package org.baebe.coffeetrading.commons.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.baebe.coffeetrading.commons.dto.ErrorMessage;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.common.ResultTypes;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.springframework.http.HttpStatus;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final ResultTypes statusType;
    private final int statusCode;
    private final T data;
    private final ErrorMessage error;

    private ApiResponse(ResultTypes statusType, int statusCode, T data, ErrorMessage error) {
        this.statusType = statusType;
        this.statusCode = statusCode;
        this.data = data;
        this.error = error;
    }

    public static <S> ApiResponse<S> successByData(S data) {
        return new ApiResponse<>(ResultTypes.SUCCESS, HttpStatus.OK.value(), data, null);
    }

    public static ApiResponse<Void> successByEmpty() {
        return new ApiResponse<>(ResultTypes.SUCCESS, HttpStatus.CREATED.value(), null, null);
    }

    public static ApiResponse<?> error(ErrorTypes error) {
        return new ApiResponse<>(ResultTypes.FAIL, error.getStatus().value(), null, new ErrorMessage(error));
    }

    public static ApiResponse<?> error(ErrorTypes error, Object errorData) {
        return new ApiResponse<>(ResultTypes.FAIL,  error.getStatus().value(), null, new ErrorMessage(error, errorData));
    }

    public static ApiResponse<?> error(ErrorTypes error, Object errorData, CoreException e) {
        return new ApiResponse<>(ResultTypes.FAIL,  error.getStatus().value(), null, new ErrorMessage(e, errorData));
    }

    public static ApiResponse<?> error(ErrorTypes error, CoreException e) {
        return new ApiResponse<>(ResultTypes.FAIL,  error.getStatus().value(), null, new ErrorMessage(e));
    }
}