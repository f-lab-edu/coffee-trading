package org.baebe.coffeetrading.api.store.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.baebe.coffeetrading.api.store.business.StoreBusiness;
import org.baebe.coffeetrading.api.store.dto.request.AddStoreRequest;
import org.baebe.coffeetrading.api.store.dto.request.ModifyStoreRequest;
import org.baebe.coffeetrading.api.store.dto.response.StoreResponse;
import org.baebe.coffeetrading.commons.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin-api/store")
@RequiredArgsConstructor
public class AdminStoreController {
    private final StoreBusiness storeBusiness;

    @PostMapping
    public ApiResponse<StoreResponse> addStore(@RequestBody AddStoreRequest request) {
        return ApiResponse.successByData(storeBusiness.addStore(request));
    }

    @GetMapping
    public ApiResponse<List<StoreResponse>> getAllStores() {
        return ApiResponse.successByData(storeBusiness.getAllStores());
    }

    @PutMapping
    public ApiResponse<StoreResponse> modifyStore(@RequestBody ModifyStoreRequest request) {
        return ApiResponse.successByData(storeBusiness.modifyStore(request));
    }

    @DeleteMapping
    public ApiResponse<Void> deleteStore(@RequestParam Long storeId) {
        storeBusiness.deleteStore(storeId);
        return ApiResponse.successByEmpty();
    }
} 