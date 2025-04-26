package org.baebe.coffeetrading.api.user.controller;

import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.api.user.business.UserBusiness;
import org.baebe.coffeetrading.api.user.dto.request.RegisterRequest;
import org.baebe.coffeetrading.api.user.dto.response.RegisterResponse;
import org.baebe.coffeetrading.commons.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {

    private final UserBusiness userBusiness;

    @GetMapping("/check-duplicate-email")
    public boolean emailDuplicateCheck(@RequestParam @Email String email) {
        return userBusiness.isExistEmail(email);
    }

    @GetMapping("/check-duplicate-nickname")
    public boolean nicknameDuplicateCheck(@RequestParam String nickname) {
        return userBusiness.isExistNickname(nickname);
    }

    @PostMapping
    public ApiResponse<RegisterResponse> userRegister(@RequestBody RegisterRequest request) {
        return ApiResponse.successByData(userBusiness.registerHandle(request));
    }
}
