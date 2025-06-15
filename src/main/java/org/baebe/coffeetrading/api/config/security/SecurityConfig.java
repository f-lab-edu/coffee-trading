package org.baebe.coffeetrading.api.config.security;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.api.config.filter.AllowedMethodsFilter;
import org.baebe.coffeetrading.api.config.filter.JwtAuthenticationFilter;
import org.baebe.coffeetrading.api.config.filter.JwtExceptionFilter;
import org.baebe.coffeetrading.domains.user.service.AuthDetailsService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String ADMIN = "ADMIN";
    private static final String PARTNER = "PARTNER";
    private static final String USER = "USER";

//    private final CorsConfigurationSource corsConfigurationSource;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AllowedMethodsFilter allowedMethodsFilter;
    private final JwtExceptionFilter jwtExceptionFilter;

    private final AuthDetailsService authDetailsService;
    private final AuthenticationConfiguration authenticationConfiguration;

    private final Set<String> permitAllRequest = Set.of(
        "/static",
        "/static/**",
        "/error",
        "/auth/**",
        "/register", "/register/**"
    );

//    private final String[] openApiRequests = new String[] {
//        "/open-api/**"
//    };

    private final String[] authApiRequests = new String[] {
        "/api/**",
        "/file/upload"
    };

    private final String[] adminApiRequests = new String[] {
        "/admin-api/**"
    };

    private final String[] partnerApiRequests = new String[] {
        "/partner-api/**"
    };

    /**
     * web에 필요한 Location으로 필요할 시 주석 해제
     */
//    @Bean
//    WebSecurityCustomizer webSecurityCustomizer() {
//        return web -> web.ignoring()
//            .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http
            .httpBasic(AbstractHttpConfigurer::disable)
            .headers(header -> header.frameOptions(FrameOptionsConfig::sameOrigin))
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
//            .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource))
            .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer.authenticationEntryPoint(customAuthenticationEntryPoint))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
            .addFilterAfter(allowedMethodsFilter, JwtExceptionFilter.class)
            .authorizeHttpRequests(authHttpRequestMatcherRegistry ->
                authHttpRequestMatcherRegistry
                    .requestMatchers(permitAllRequest.toArray(String[]::new)).permitAll()
//                    .requestMatchers(openApiRequests).permitAll()
                    .requestMatchers(authApiRequests).authenticated()
                    .requestMatchers(adminApiRequests).hasAnyRole(ADMIN)
                    .requestMatchers(partnerApiRequests).hasAnyRole(PARTNER, ADMIN)
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .anyRequest().authenticated()
            )
            .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(authDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
