package org.baebe.coffeetrading.api.user.controller;

import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.api.user.business.UserBusiness;
import org.baebe.coffeetrading.api.user.dto.request.MyStoresRequest;
import org.baebe.coffeetrading.api.user.dto.request.MobileAuthenticationRequest;
import org.baebe.coffeetrading.api.user.dto.request.ModifyPasswordRequest;
import org.baebe.coffeetrading.api.user.dto.response.MyStoreInfoResponse;
import org.baebe.coffeetrading.api.user.dto.response.MyStoresResponse;
import org.baebe.coffeetrading.commons.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserBusiness userBusiness;

    @PatchMapping("/mobile-authentication")
    public void mobileAuthentication(@RequestBody MobileAuthenticationRequest request, Principal principal) {
        userBusiness.authenticationByMobile(principal.getName(), request);
    }

    @PatchMapping("/modify-password")
    public void modifyPassword(@RequestBody ModifyPasswordRequest request, Principal principal) {
        userBusiness.modifyPassword(principal.getName(), request);
    }

    @DeleteMapping("/disable")
    public void disable(Principal principal) {
        userBusiness.disableUser(principal.getName());
    }

    @GetMapping("/stores")
    public ApiResponse<List<MyStoresResponse>> myStores(Principal principal) {
        return ApiResponse.successByData(userBusiness.getMyStoreList(principal.getName()));
    }

    @GetMapping("/stores-info")
    public ApiResponse<List<MyStoreInfoResponse>> myStoresInfo(@RequestParam String userStoreGroupId) {
        return ApiResponse.successByData(userBusiness.getMyStoreListInfo(userStoreGroupId));
    }

    @PostMapping("/stores")
    public void addStoreList(Principal principal, String myStoreListName){
        userBusiness.addMyStoreList(principal.getName(), myStoreListName);
    }

    @PostMapping("/store-info")
    public void addStore(MyStoresRequest request){
        userBusiness.myStoreHandle(request);
    }

    @DeleteMapping("/stores")
    public void deleteStoreList(@RequestParam String userStoreGroupId){
        userBusiness.removeMyStoreList(userStoreGroupId);
    }
    @DeleteMapping("/store-info")
    public void deleteStore(@RequestBody MyStoresRequest request){
        userBusiness.removeMyStoreListInfo(request);
    }
}
