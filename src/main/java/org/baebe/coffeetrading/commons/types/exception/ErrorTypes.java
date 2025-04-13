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
    BAD_REQUESTS(BAD_REQUEST, WARN, "잘못된 요청입니다."),
    BAD_ILLEAGAL_ARGUMENT(BAD_REQUEST, WARN, "값이 잘못 입력되었습니다. 다시 확인해 주세요."),

    UN_AUTHORIZED(UNAUTHORIZED, WARN, "접근 권한이 없습니다."),

    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, ERROR, "허용되지 않은 HTTP 메소드입니다."),

    TOKEN_EXPIRED(UNAUTHORIZED, WARN, "로그인 시간이 만료되었습니다. 다시 로그인해 주세요."),

    INTERNAL_ERROR(INTERNAL_SERVER_ERROR, ERROR, "알 수 없는 오류가 발생하였습니다."),
//    CORS_ORIGINS_ERROR(INTERNAL_SERVER_ERROR, ERROR, "CorsProperties requires origins setting"),
//    CORS_ALLOWED_METHOD_ERROR(INTERNAL_SERVER_ERROR, ERROR, "CorsProperties requires allowedMethods setting"),

    DUPLICATE_USER_ID(BAD_REQUEST, WARN, "해당 이메일은 사용 중입니다."),
    DUPLICATE_NICKNAME(BAD_REQUEST, WARN, "해당 닉네임은 사용 중입니다."),

    DUPLICATE_USER(BAD_REQUEST, WARN, "해당 정보로 인증된 계정이 존재합니다."),
    USER_NOT_FOUND(NOT_FOUND, INFO, "아이디 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요."),

    FAILED_LOGIN_NAVER(BAD_REQUEST, WARN, "네이버 간편 로그인에 실패하였습니다."),

    NOT_EQUALS_PASSWORD(BAD_REQUEST, WARN, "변경 전 비밀번호가 일치하지 않습니다. 다시 입력해 주세요."),
    CHANGE_PASSWORD_EQUALS(BAD_REQUEST, WARN, "변경 전 비밀번호와 같습니다. 변경을 위해 다른 비밀번호를 입력해 주세요."),

    MULTI_USER_ERROR(BAD_REQUEST, ERROR, "사용자 인증에 문제가 발생하였습니다. 관리자에게 문의해 주세요."),

    BAD_TOKEN_TYPE(BAD_REQUEST, WARN, "토큰 타입이 잘못되었습니다.");

    private final HttpStatus status;
    private final LogLevel logLevel;
    private final String errorMessage;
}
