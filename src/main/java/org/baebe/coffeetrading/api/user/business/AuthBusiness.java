package org.baebe.coffeetrading.api.user.business;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.api.user.dto.request.LoginRequest;
import org.baebe.coffeetrading.api.user.dto.request.TokenRequest;
import org.baebe.coffeetrading.api.user.dto.response.LoginResponse;
import org.baebe.coffeetrading.commons.annotation.Business;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.commons.types.user.UserRole;
import org.baebe.coffeetrading.domains.user.entity.UsersEntity;
import org.baebe.coffeetrading.domains.user.jwt.dto.vo.JwtTokenDto;
import org.baebe.coffeetrading.domains.user.jwt.helper.JwtLocalManager;
import org.baebe.coffeetrading.domains.user.jwt.helper.JwtTokenProvider;
import org.baebe.coffeetrading.domains.user.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

@Slf4j
@Business
@RequiredArgsConstructor
public class AuthBusiness {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtLocalManager jwtLocalManager;
    private final AuthenticationManager authenticationManager;

    public LoginResponse loginHandle(LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        validateLogin(email, password);

        UsersEntity user = userService.getByUserEmail(email);
        Long userId = user.getId();
        UserRole userType = user.getUserType();

        Date issuedAt = new Date();
        String accessToken = jwtTokenProvider.generatedAccessToken(userId, userType, issuedAt);
        String refreshToken = jwtTokenProvider.generatedRefreshToken(userId, userType, issuedAt);

        jwtLocalManager.addUserToken(userId.toString(), createJwtTokenDto(accessToken, refreshToken));

        return LoginResponse.of(accessToken, refreshToken, user);
    }

    public void logout(String userId) {

        UsersEntity user = userService.getByUserId(Long.parseLong(userId));
        log.info("Logout userId : {}, userEmail : {}", user.getId(), user.getEmail());
        jwtLocalManager.removeUserToken(user.getId().toString());
    }

    public LoginResponse refreshHandle(TokenRequest tokenRequest) {
        String refreshToken = tokenRequest.getRefreshToken();

        if (!jwtTokenProvider.validateRefreshTokenInRedis(refreshToken)) {
            throw new CoreException(ErrorTypes.TOKEN_EXPIRED);
        }

        Long userId = Long.parseLong(jwtTokenProvider.getUserIdByToken(refreshToken));
        UsersEntity user = userService.getByUserId(userId);
        UserRole userType = user.getUserType();

        Date issuedAt = new Date();
        String newAccessToken = jwtTokenProvider.generatedAccessToken(userId, userType, issuedAt);
        String newRefreshToken = jwtTokenProvider.generatedRefreshToken(userId, userType, issuedAt);

        jwtLocalManager.addUserToken(userId.toString(), createJwtTokenDto(newAccessToken, newRefreshToken));

        return LoginResponse.of(newAccessToken, newRefreshToken, user);
    }

    private void validateLogin(String email, String password) {

        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);
            authenticationManager.authenticate(authToken);
        } catch (AuthenticationException e) {
            throw new CoreException(ErrorTypes.USER_NOT_FOUND, null, e);
        }
    }

    private JwtTokenDto createJwtTokenDto(String accessToken, String refreshToken) {
        return new JwtTokenDto(accessToken, refreshToken, LocalDateTime.now());
    }
}
