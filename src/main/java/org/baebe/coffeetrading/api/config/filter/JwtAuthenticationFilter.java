package org.baebe.coffeetrading.api.config.filter;

import static org.baebe.coffeetrading.domains.user.jwt.TokenConstants.BEARER;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.api.config.security.CustomUserDetails;
import org.baebe.coffeetrading.domains.user.jwt.dto.vo.AccessTokenDto;
import org.baebe.coffeetrading.domains.user.jwt.helper.JwtTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {

        Optional<String> accessTokenOptional = parseRequestToken(request)
            .filter(jwtTokenProvider::isAccessToken);

        if (accessTokenOptional.isPresent()) {
            String accessToken = accessTokenOptional.get();

            if (jwtTokenProvider.validateAccessTokenInRedis(accessToken)) {
                Authentication authentication = getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } else {
            log.debug("No token found in request");
        }
        filterChain.doFilter(request, response);
    }

    private Optional<String> parseRequestToken(HttpServletRequest request) {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token == null || !token.startsWith(BEARER)) {
            return Optional.empty();
        }
        return Optional.of(token.substring(BEARER.length()));
    }

    private Authentication getAuthentication(String token) {
        AccessTokenDto accessTokenDetail = jwtTokenProvider.parseAccessToken(token);

        UserDetails userDetails = CustomUserDetails.of(
            accessTokenDetail.id().toString(),
            accessTokenDetail.role()
        );

        return new UsernamePasswordAuthenticationToken(
            userDetails, null, userDetails.getAuthorities()
        );
    }
}
