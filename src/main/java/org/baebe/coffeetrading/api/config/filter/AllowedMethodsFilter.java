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
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class AllowedMethodsFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Value("${ALLOWED_METHODS}")
    private String allowedMethods;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(allowedMethods.contains(request.getMethod())) {
            filterChain.doFilter(request, response);
            return;
        }

        log.error("[허용 불가 메소드 요청] Request User >>> {}, accIp >>> {}, RequestURL >>> {}, RequestMethod  >>> {}",
            request.getRemoteUser(), request.getRemoteAddr(), request.getRequestURL(), request.getMethod()
        );

        response.setStatus(ErrorTypes.METHOD_NOT_ALLOWED.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.error(ErrorTypes.METHOD_NOT_ALLOWED)));
    }
}
