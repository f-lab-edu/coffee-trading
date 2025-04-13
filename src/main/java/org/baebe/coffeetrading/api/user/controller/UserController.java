package org.baebe.coffeetrading.api.user.controller;

import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.api.user.business.UserBusiness;
import org.baebe.coffeetrading.api.user.dto.request.MobileAuthenticationRequest;
import org.baebe.coffeetrading.api.user.dto.request.ModifyPasswordRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserBusiness userBusiness;

    @PostMapping("/mobile-authentication")
    public void mobileAuthentication(@RequestBody MobileAuthenticationRequest request, Principal principal) {
        userBusiness.authenticationByMobile(principal.getName(), request);
    }

    @PostMapping("/modify-password")
    public void modifyPassword(@RequestBody ModifyPasswordRequest request, Principal principal) {
        userBusiness.modifyPassword(principal.getName(), request);
    }

    @GetMapping("/disable")
    public void disable(Principal principal) {
        userBusiness.disableUser(principal.getName());
    }
}
