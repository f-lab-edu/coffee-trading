package org.baebe.coffeetrading.api.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.commons.dto.response.ApiResponse;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 필터 처리과정에서 인증 인가 부분에 대한 오류를 일반
 * API Response로 Body를 변형해서 보내주는 역할을 한다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            String responseMessage = "";
            ErrorTypes errorType;
            if (e instanceof CoreException coreException) {
                log.error("CoreException : {}", coreException.getMessage(), coreException);

                errorType = coreException.getErrorTypes();
                ApiResponse<?> body = ApiResponse.error(
                    coreException.getErrorTypes(),
                    coreException.getData(),
                    coreException
                );
                responseMessage = objectMapper.writeValueAsString(body);
            } else {
                log.error("Exception : {}", e.getMessage(), e);
                errorType = ErrorTypes.INTERNAL_ERROR;
                ApiResponse<?> body = ApiResponse.error(errorType);
                responseMessage = objectMapper.writeValueAsString(body);
            }

            response.setStatus(errorType.getStatus().value());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().write(responseMessage);
        }
    }
}
