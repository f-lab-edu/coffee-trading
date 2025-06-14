package org.baebe.coffeetrading.api.store.controller;

import lombok.RequiredArgsConstructor;
import org.baebe.coffeetrading.api.store.business.StoreBusiness;
import org.baebe.coffeetrading.api.store.dto.request.StoreRequest;
import org.baebe.coffeetrading.api.store.dto.response.StoreResponse;
import org.baebe.coffeetrading.commons.dto.response.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {
    private final StoreBusiness storeBusiness;

    @PostMapping("/search")
    public ApiResponse<Page<StoreResponse>> getStores(@RequestBody StoreRequest request) {
        return ApiResponse.successByData(storeBusiness.getStores(request));
    }
} 