package org.baebe.coffeetrading.api.config.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
    JwtProperties.class,
    CorsProperties.class,
    NaverProperties.class
})
public class ConfigurationProperties {

}
