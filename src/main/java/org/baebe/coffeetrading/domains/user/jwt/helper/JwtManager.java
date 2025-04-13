package org.baebe.coffeetrading.domains.user.jwt.helper;

import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.commons.utils.redis.RedisUtils;
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
        Map<String, String> jwtTokenMap = RedisUtils.toJwtTokenMap(token);
        redisTemplate.opsForHash().putAll(userKey, jwtTokenMap);
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
        return RedisUtils.fromJwtTokenMap(tokenMap);
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
