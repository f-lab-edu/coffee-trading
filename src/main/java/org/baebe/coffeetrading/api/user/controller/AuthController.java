package org.baebe.coffeetrading.api.user.controller;

import static org.baebe.coffeetrading.commons.types.user.AccountTypes.*;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.api.user.business.AuthBusiness;
import org.baebe.coffeetrading.api.user.business.NaverAuthBusiness;
import org.baebe.coffeetrading.api.user.dto.request.LoginRequest;
import org.baebe.coffeetrading.api.user.dto.request.TokenRequest;
import org.baebe.coffeetrading.api.user.dto.response.LoginResponse;
import org.baebe.coffeetrading.commons.types.user.AccountTypes;
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

    private final AuthBusiness authBusiness;
    private final NaverAuthBusiness naverAuthBusiness;

    @PostMapping("/login/code/web")
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) {
        return authBusiness.loginHandle(loginRequest);
    }

    @PostMapping("/logout")
    public void logout(Principal principal) {
        authBusiness.logout(principal.getName());
    }

    @PostMapping("/refresh")
    public LoginResponse refresh(@RequestBody @Validated TokenRequest tokenRequest) {
        return authBusiness.refreshHandle(tokenRequest);
    }
}
