package org.baebe.coffeetrading.api.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.commons.utils.common.ResponseUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
        AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn("Access Denied >>> {}", accessDeniedException.getMessage(), accessDeniedException);
        ResponseUtils.setErrorResponse(response, objectMapper, ErrorTypes.FORBIDDEN);
    }
}
