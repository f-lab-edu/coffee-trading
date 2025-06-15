package org.baebe.coffeetrading.api.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.commons.utils.common.ResponseUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 필터 처리과정에서 인증 부분에 대한 오류를 일반
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
        } catch (JwtException e) {
            log.error("JwtException >>> {}", e.getMessage(), e);
            ResponseUtils.setErrorResponse(response, objectMapper, ErrorTypes.UN_AUTHORIZED);
        } catch (CoreException e) {
            log.error("CoreException : {}", e.getMessage(), e);
            ResponseUtils.setErrorResponse(response, objectMapper, e.getErrorTypes(), e);
        }
    }
}
