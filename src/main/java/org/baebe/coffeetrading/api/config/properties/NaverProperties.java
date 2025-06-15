package org.baebe.coffeetrading.api.config.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.naver")
public class NaverProperties {

    private String clientId;
    private String clientSecret;
    private String redirectUri;
    private final String tokenUrl = "https://nid.naver.com/oauth2.0/token";
    private String authorizationGrantType;
}