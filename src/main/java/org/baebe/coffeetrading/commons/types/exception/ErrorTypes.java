package org.baebe.coffeetrading.commons.types.exception;

import static org.springframework.boot.logging.LogLevel.*;
import static org.springframework.http.HttpStatus.*;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum ErrorTypes {

    BAD_ILLEAGAL_ARGUMENT(BAD_REQUEST, WARN, "값이 잘못 입력되었습니다. 다시 확인해 주세요."),

    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, ERROR, "알 수 없는 오류가 발생하였습니다."),
    CORS_ORIGINS_ERROR(INTERNAL_SERVER_ERROR, ERROR, "CorsProperties requires origins setting"),
    CORS_ALLOWED_METHOD_ERROR(INTERNAL_SERVER_ERROR, ERROR, "CorsProperties requires allowedMethods setting");

    private final HttpStatus status;
    private final LogLevel logLevel;
    private final String errorMessage;
}
