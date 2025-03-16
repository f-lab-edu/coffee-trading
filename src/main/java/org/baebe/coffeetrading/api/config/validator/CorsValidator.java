package org.baebe.coffeetrading.api.config.validator;

import jakarta.annotation.PostConstruct;
import org.baebe.coffeetrading.api.config.properties.CorsProperties;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CorsValidator {

    private final CorsProperties corsProperties;

    @Autowired
    public CorsValidator(CorsProperties corsProperties) {
        this.corsProperties = corsProperties;
    }

    @PostConstruct
    public void validateCorsProperties() {
        if (corsProperties.getOrigins() == null ||
            corsProperties.getOrigins().isEmpty()) {
            throw new CoreException(ErrorTypes.CORS_ORIGINS_ERROR);
        }

        if (corsProperties.getAllowedMethods() == null ||
            corsProperties.getAllowedMethods().isEmpty()) {
            throw new CoreException(ErrorTypes.CORS_ALLOWED_METHOD_ERROR);
        }
    }
}
