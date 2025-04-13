//package org.baebe.coffeetrading.api.config.security;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.baebe.coffeetrading.api.config.properties.CorsProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//@Slf4j
//@Configuration
//@RequiredArgsConstructor
//public class CorsConfig {
//
//    private final CorsProperties corsProperties;
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        corsProperties.getOrigins().forEach(origin -> log.debug("Allowed Cors Origins >>> {}", origin));
//
//        CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.setAllowedOriginPatterns(corsProperties.getOrigins());
//        config.setAllowedMethods(corsProperties.getAllowedMethods());
//        config.addAllowedHeader(CorsConfiguration.ALL);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//        return source;
//    }
//}
