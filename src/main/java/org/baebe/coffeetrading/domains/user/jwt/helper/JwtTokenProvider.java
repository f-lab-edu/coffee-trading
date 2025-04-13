package org.baebe.coffeetrading.domains.user.jwt.helper;

import static org.baebe.coffeetrading.domains.user.jwt.TokenConstants.ACCESS_TOKEN;
import static org.baebe.coffeetrading.domains.user.jwt.TokenConstants.REFRESH_TOKEN;
import static org.baebe.coffeetrading.domains.user.jwt.TokenConstants.ROLE;
import static org.baebe.coffeetrading.domains.user.jwt.TokenConstants.TOKEN_TYPE;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.api.config.properties.JwtProperties;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.commons.types.user.UserRole;
import org.baebe.coffeetrading.domains.user.jwt.dto.vo.AccessTokenDto;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final JwtManager jwtManager;

    /**
     * AccessToken 재갱신 시 사용
     */
    public String generatedAccessToken(Long userId, UserRole role, final Date issuedAt) {
        final Date accessTokenExpiresIn = new Date(issuedAt.getTime() + (jwtProperties.getAccessExp() * 1000));

        return buildAccessToken(userId, issuedAt, accessTokenExpiresIn, role);
    }

    /**
     * RefreshToken 재갱신 시 사용
     */
    public String generatedRefreshToken(Long userId, UserRole role, final Date issuedAt){
        final Date refreshTokenExpiresIn = new Date(issuedAt.getTime() + (jwtProperties.getRefreshExp() * 1000));

        return buildRefreshToken(userId, issuedAt, refreshTokenExpiresIn, role);
    }

    public AccessTokenDto parseAccessToken(String token) {
        if (isAccessToken(token)) {
            Claims claims = getJws(token).getBody();
            return new AccessTokenDto(
                Long.parseLong(claims.getSubject()),
                UserRole.valueOf((String) claims.get(ROLE))
            );
        }

        throw new CoreException(ErrorTypes.BAD_TOKEN_TYPE);
    }

    /**
     * Redis에 요청토큰이 있는지 검증
     */
    public boolean validateAccessTokenInRedis(String token) {
        if (isAccessToken(token)) {
            Jws<Claims> jws = getJws(token);
            String userId =  jws.getBody().getSubject();
            return jwtManager.isValidCompareAccessToken(userId, token);
        }

        throw new CoreException(ErrorTypes.BAD_TOKEN_TYPE);
    }

    /**
     * Redis에 RefreshToken이 있는지 검증
     */

    public boolean validateRefreshTokenInRedis(String token) {
        if (isRefreshToken(token)) {
            Jws<Claims> jws = getJws(token);
            String userSn =  jws.getBody().getSubject();
            return jwtManager.isValidCompareRefreshToken(userSn, token);
        }

        throw new CoreException(ErrorTypes.BAD_TOKEN_TYPE);
    }


    public boolean isAccessToken(String token) {
        return getTokenType(token).equals(ACCESS_TOKEN);
    }

    public boolean isRefreshToken(String token) {
        return getTokenType(token).equals(REFRESH_TOKEN);
    }

    public String getUserIdByToken(String token) {
        return getJws(token).getBody().getSubject();
    }

    private String getTokenType(String token) {
        return (String)getJws(token).getBody().get(TOKEN_TYPE);
    }

    private String buildAccessToken(Long userId, Date issuedAt, Date accessTokenExpiresIn, UserRole role) {
        final Key encodedKey = getSecretKey();
        return Jwts.builder()
            .setIssuer(jwtProperties.getIssuer())
            .setIssuedAt(issuedAt)
            .setSubject(userId.toString())
            .claim(TOKEN_TYPE, ACCESS_TOKEN)
            .claim(ROLE, role)
            .setExpiration(accessTokenExpiresIn)
            .signWith(encodedKey, SignatureAlgorithm.HS512)
            .compact();
    }

    private String buildRefreshToken(Long userId, Date issuedAt, Date refreshTokenExpiresIn, UserRole role) {
        final Key encodedKey = getSecretKey();
        return Jwts.builder()
            .setIssuer(jwtProperties.getIssuer())
            .setIssuedAt(issuedAt)
            .setSubject(userId.toString())
            .claim(TOKEN_TYPE, REFRESH_TOKEN)
            .claim(ROLE, role)
            .setExpiration(refreshTokenExpiresIn)
            .signWith(encodedKey, SignatureAlgorithm.HS512)
            .compact();
    }

    private Jws<Claims> getJws(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new CoreException(ErrorTypes.TOKEN_EXPIRED);
        } catch (Exception e) {
            throw e;
        }
    }

    private Key getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }
}
