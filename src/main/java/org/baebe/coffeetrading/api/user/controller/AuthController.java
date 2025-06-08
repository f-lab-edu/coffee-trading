package org.baebe.coffeetrading.api.user.controller;

import static org.baebe.coffeetrading.commons.types.user.AccountTypes.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.api.user.business.AuthFacade;
import org.baebe.coffeetrading.api.user.business.NaverAuthBusiness;
import org.baebe.coffeetrading.api.user.dto.request.LoginRequest;
import org.baebe.coffeetrading.api.user.dto.request.LogoutRequest;
import org.baebe.coffeetrading.api.user.dto.request.TokenRequest;
import org.baebe.coffeetrading.api.user.dto.response.LoginResponse;
import org.baebe.coffeetrading.commons.dto.response.ApiResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthFacade authFacade;
    private final NaverAuthBusiness naverAuthBusiness;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@RequestBody @Validated LoginRequest loginRequest) {
        return ApiResponse.successByData(authFacade.loginHandle(loginRequest));
    }

    @GetMapping("/login/naver")
    public ApiResponse<LoginResponse> loginByNaver(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state) {
        return ApiResponse.successByData(naverAuthBusiness.loginByOAuth(code, NAVER, state));
    }

    @PostMapping("/logout")
    public void logout(LogoutRequest request) {
        authFacade.logout(request);
    }

    @PostMapping("/refresh")
    public ApiResponse<LoginResponse> refresh(@RequestBody @Validated TokenRequest tokenRequest) {
        return ApiResponse.successByData(authFacade.refreshHandle(tokenRequest));
    }
}
