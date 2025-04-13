package org.baebe.coffeetrading.domains.user.jwt.helper;

import java.util.Optional;
import org.baebe.coffeetrading.domains.user.jwt.dto.vo.JwtTokenDto;

public abstract class JwtManager {

    private static final String TOKEN_KEY = "USER_ID_";

    /**
     * 로그인시 Redis에 토큰을 저장할때 사용
     */
    abstract public void addUserToken(String userId, JwtTokenDto token);

    /**
     * 로그아웃시 토큰이 존재하면 삭제한다.
     */
    abstract public void removeUserToken(String userId);

    /**
     * 현재 사용자 SN으로 저장되어 있는 토큰이 있는 경우 반환한다.
     */
    abstract public Optional<JwtTokenDto> getUserToken(String userId);

    /**
     * 현재 Http 요청을 보낸 토큰이 로그인을 하여 저장이 되어 있는 지를 확인한다
     */
    abstract public boolean isValidCompareAccessToken(String userId, String accessToken);

    /**
     * Refresh토큰 존재 검증
     */
    abstract public boolean isValidCompareRefreshToken(String userSn, String refreshToken);

    protected String getUserKey(String userId) {
        return TOKEN_KEY + userId;
    }
}
