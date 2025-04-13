package org.baebe.coffeetrading.domains.user.jwt.dto.vo;

import java.time.LocalDateTime;

public record JwtTokenDto(String accessToken, String refreshToken, LocalDateTime createdAt) {

    public JwtTokenDto refreshAccessToken(String accessToken) {
        return new JwtTokenDto(accessToken, this.refreshToken, LocalDateTime.now());
    }

    public JwtTokenDto refreshRefreshToken(String refreshToken) {
        return new JwtTokenDto(this.accessToken, refreshToken, LocalDateTime.now());
    }

    public JwtTokenDto refreshAllToken(String accessToken, String refreshToken) {
        return new JwtTokenDto(accessToken, refreshToken, LocalDateTime.now());
    }
}
