package org.baebe.coffeetrading.api.config.properties;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Properties의 값을 불러와야하는 클래스를 정의한다.
 */

@Configuration
@EnableConfigurationProperties({
    JwtProperties.class,
    CorsProperties.class
})
public class ConfigurationProperties {

}
