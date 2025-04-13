package org.baebe.coffeetrading.api.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.commons.dto.response.ApiResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * ResponseBody를 ApiResponse로 변환
 *
 * 에러 케이스는 ExceptionRestResponseAdvice에서 ApiResponse로 변환 처리하고 있어 그대로 반환
 * 성공 케이스는 ApiResponse 반환 타입의 경우 그대로 반환
 */

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ResponseBodyProcessAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(
        Object body,
        MethodParameter returnType,
        MediaType selectedContentType,
        Class selectedConverterType,
        ServerHttpRequest request,
        ServerHttpResponse response
    ) {
        HttpServletResponse servletResponse =
            ((ServletServerHttpResponse) response).getServletResponse();

        int status;
        if (body instanceof ApiResponse<?>) {
            servletResponse.setStatus(((ApiResponse<?>) body).getStatusCode());
            return body;
        } else {
            status = servletResponse.getStatus();
            servletResponse.setStatus(status);
        }

        HttpStatus resolve = HttpStatus.resolve(status);
        if (resolve == null) {
            return body;
        }

        if (resolve.is2xxSuccessful()) {
            if (body == null) {
                return ApiResponse.successByEmpty();
            }
            return ApiResponse.successByData(body);
        }

        return body;
    }
}
