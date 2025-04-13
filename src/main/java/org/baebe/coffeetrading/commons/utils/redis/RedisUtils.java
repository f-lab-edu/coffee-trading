package org.baebe.coffeetrading.commons.utils.redis;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.baebe.coffeetrading.domains.user.jwt.dto.vo.JwtTokenDto;

public class RedisUtils {

    public static Map<String, String> toJwtTokenMap(JwtTokenDto jwtTokenDto) {

        return Map.of(
            "accessToken", jwtTokenDto.accessToken(),
            "refreshToken", jwtTokenDto.refreshToken(),
            "createdAt", jwtTokenDto.createdAt().toString());
    }

    public static Optional<JwtTokenDto> fromJwtTokenMap(Map<Object, Object> map) {

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
}
