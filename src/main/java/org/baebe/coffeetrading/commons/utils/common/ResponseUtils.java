package org.baebe.coffeetrading.commons.utils.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.baebe.coffeetrading.commons.dto.response.ApiResponse;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.springframework.http.MediaType;

public class ResponseUtils {

    public static void setErrorResponse(
        HttpServletResponse response,
        ObjectMapper objectMapper,
        ErrorTypes errorType
    ) throws IOException {

        ApiResponse<?> body = ApiResponse.error(errorType);
        String responseMessage = objectMapper.writeValueAsString(body);

        response.setStatus(errorType.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(responseMessage);
    }

    public static void setErrorResponse(
        HttpServletResponse response,
        ObjectMapper objectMapper,
        ErrorTypes errorType,
        CoreException coreException
    ) throws IOException {

        ApiResponse<?> body = ApiResponse.error(
            coreException.getErrorTypes(),
            coreException.getData(),
            coreException
        );

        String responseMessage = objectMapper.writeValueAsString(body);

        response.setStatus(errorType.getStatus().value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(responseMessage);
    }
}
