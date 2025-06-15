package org.baebe.coffeetrading.api.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.commons.utils.common.ResponseUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {
//        log.warn("Authentication failed >>>", authException);
//        throw new CoreException(ErrorTypes.UN_AUTHORIZED);

        log.warn("Authentication failed >>> {}", authException.getMessage(), authException);

        if (authException.getCause() instanceof CoreException coreException) {
            ResponseUtils.setErrorResponse(response, objectMapper, coreException.getErrorTypes());
        } else {
            ResponseUtils.setErrorResponse(response, objectMapper, ErrorTypes.UN_AUTHORIZED);
        }
    }
}
