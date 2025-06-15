package org.baebe.coffeetrading.api.user.business;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.api.user.dto.request.LoginRequest;
import org.baebe.coffeetrading.api.user.dto.request.LogoutRequest;
import org.baebe.coffeetrading.api.user.dto.request.TokenRequest;
import org.baebe.coffeetrading.api.user.dto.response.LoginResponse;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.commons.types.user.UserRole;
import org.baebe.coffeetrading.domains.user.entity.UsersEntity;
import org.baebe.coffeetrading.domains.user.jwt.dto.vo.JwtTokenDto;
import org.baebe.coffeetrading.domains.user.jwt.helper.JwtManager;
import org.baebe.coffeetrading.domains.user.jwt.helper.JwtTokenProvider;
import org.baebe.coffeetrading.domains.user.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthFacade {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtManager jwtManager;
    private final AuthenticationManager authenticationManager;

    public LoginResponse loginHandle(LoginRequest loginRequest) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        validateLogin(email, password);

        return generateLoginResponse(email);
    }

    public LoginResponse oauth2LoginHandle(String email) {

        return generateLoginResponse(email);
    }

    public void logout(LogoutRequest request) {

        UsersEntity user = userService.getByUserId(Long.parseLong(request.getUserId()));
        log.info("Logout userSn : {}, userEmail : {}", request.getUserId(), user.getEmail());
        jwtManager.addUserToken(request.getUserId(), createJwtTokenDto(request.getAccessToken(), request.getRefreshToken()));
    }

    public LoginResponse refreshHandle(TokenRequest tokenRequest) {
        String refreshToken = tokenRequest.getRefreshToken();

        if (jwtTokenProvider.validateRefreshTokenInRedis(refreshToken)) {
            throw new CoreException(ErrorTypes.UN_AUTHORIZED);
        }

        Long userId = Long.parseLong(jwtTokenProvider.getUserIdByToken(refreshToken));
        UsersEntity user = userService.getByUserId(userId);
        UserRole userType = user.getUserType();

        Date issuedAt = new Date();
        String newAccessToken = jwtTokenProvider.generatedAccessToken(userId, userType, issuedAt);
        String newRefreshToken = jwtTokenProvider.generatedRefreshToken(userId, userType, issuedAt);

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

    private LoginResponse generateLoginResponse(String email) {

        UsersEntity user = userService.getByUserEmail(email);
        Long userId = user.getId();
        UserRole userType = user.getUserType();

        Date issuedAt = new Date();
        String accessToken = jwtTokenProvider.generatedAccessToken(userId, userType, issuedAt);
        String refreshToken = jwtTokenProvider.generatedRefreshToken(userId, userType, issuedAt);

        return LoginResponse.of(accessToken, refreshToken, user);
    }
}
