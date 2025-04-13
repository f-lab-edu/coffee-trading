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
public class JwtLocalManager extends JwtManager{

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void addUserToken(String userSn, JwtTokenDto token) {
        String userKey = getUserKey(userSn);

        if (getUserToken(userSn).isPresent()) {
            removeUserToken(userSn);
        }
        Map<String, String> jwtTokenMap = RedisUtils.toJwtTokenMap(token);
        redisTemplate.opsForHash().putAll(userKey, jwtTokenMap);
    }

    @Override
    public void removeUserToken(String userSn) {
        String userKey = getUserKey(userSn);
        redisTemplate.delete(userKey);
    }

    @Override
    public Optional<JwtTokenDto> getUserToken(String userSn) {
        String userKey = getUserKey(userSn);

        Map<Object, Object> tokenMap = redisTemplate.opsForHash().entries(userKey);
        return RedisUtils.fromJwtTokenMap(tokenMap);
    }

    @Override
    public boolean isValidCompareAccessToken(String userSn, String accessToken) {
        if (accessToken == null) {
            throw new CoreException(ErrorTypes.BAD_TOKEN_TYPE);
        }
        JwtTokenDto jwtToken = getUserToken(userSn)
            .orElseThrow(() -> new CoreException(ErrorTypes.TOKEN_EXPIRED));

        return accessToken.equals(jwtToken.accessToken());
    }

    @Override
    public boolean isValidCompareRefreshToken(String userSn, String refreshToken) {

        if (refreshToken == null) throw new CoreException(ErrorTypes.BAD_REQUESTS);

        return getUserToken(userSn).map(jwtTokenDto ->
            jwtTokenDto.refreshToken().equals(refreshToken))
            .orElseThrow(() -> new CoreException(ErrorTypes.TOKEN_EXPIRED));
    }
}
