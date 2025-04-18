package org.baebe.coffeetrading.api.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "auth.jwt")
public class JwtProperties {
    private String secretKey;
    private Long accessExp;
    private Long refreshExp;
    private String issuer;
}