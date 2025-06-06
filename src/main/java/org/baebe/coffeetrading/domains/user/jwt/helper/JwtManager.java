package org.baebe.coffeetrading.domains.user.jwt.helper;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.domains.user.jwt.dto.vo.JwtTokenDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtManager {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 로그인시 Redis에 토큰을 저장할때 사용
     */
    public void addUserToken(String userSn, JwtTokenDto token) {
        String userKey = getUserKey(userSn);

        if (getUserToken(userSn).isPresent()) {
            removeUserToken(userSn);
        }
        Map<String, String> jwtTokenMap = toJwtTokenMap(token);
        redisTemplate.opsForHash().putAll(userKey, jwtTokenMap);
    }

    private Map<String, String> toJwtTokenMap(JwtTokenDto jwtTokenDto) {

        return Map.of(
            "accessToken", jwtTokenDto.accessToken(),
            "refreshToken", jwtTokenDto.refreshToken(),
            "createdAt", jwtTokenDto.createdAt().toString());
    }

    /**
     * 로그아웃시 토큰이 존재하면 삭제한다.
     */
    public void removeUserToken(String userSn) {
        String userKey = getUserKey(userSn);
        redisTemplate.delete(userKey);
    }
    /**
     * 현재 사용자 SN으로 저장되어 있는 토큰이 있는 경우 반환한다.
     */
    public Optional<JwtTokenDto> getUserToken(String userSn) {
        String userKey = getUserKey(userSn);

        Map<Object, Object> tokenMap = redisTemplate.opsForHash().entries(userKey);
        return fromJwtTokenMap(tokenMap);
    }

    private Optional<JwtTokenDto> fromJwtTokenMap(Map<Object, Object> map) {

        if (map == null || map.isEmpty()) return Optional.empty();

        Map<String, String> toStringMap = map.entrySet().stream()
            .filter(e -> e.getKey() != null && e.getValue() != null)
            .collect(Collectors.toMap(
                e -> e.getKey().toString(),
                e -> e.getValue().toString()
            ));

        String accessToken = toStringMap.get("accessToken");
        String refreshToken = toStringMap.get("refreshToken");

        if (toStringMap.get("createdAt") == null || toStringMap.get("createdAt").isEmpty()) {
            return Optional.of(new JwtTokenDto(accessToken, refreshToken, null));
        }

        LocalDateTime createdAt = LocalDateTime.parse(toStringMap.get("createdAt"));
        return Optional.of(new JwtTokenDto(accessToken, refreshToken, createdAt));
    }

    /**
     * 현재 Http 요청을 보낸 토큰이 로그인을 하여 저장이 되어 있는 지를 확인한다
     */
    public boolean isValidCompareAccessToken(String userSn, String accessToken) {
        if (accessToken == null) {
            throw new CoreException(ErrorTypes.BAD_TOKEN_TYPE);
        }
        JwtTokenDto jwtToken = getUserToken(userSn)
            .orElseThrow(() -> new CoreException(ErrorTypes.TOKEN_EXPIRED));

        return accessToken.equals(jwtToken.accessToken());
    }
    /**
     * Refresh토큰 존재 검증
     */
    public boolean isValidCompareRefreshToken(String userSn, String refreshToken) {

        if (refreshToken == null) throw new CoreException(ErrorTypes.BAD_REQUESTS);

        return getUserToken(userSn).map(jwtTokenDto ->
                jwtTokenDto.refreshToken().equals(refreshToken))
            .orElseThrow(() -> new CoreException(ErrorTypes.TOKEN_EXPIRED));
    }
    private String getUserKey(String userId) {
        final String TOKEN_KEY = "USER_ID_";
        return TOKEN_KEY + userId;
    }
}
