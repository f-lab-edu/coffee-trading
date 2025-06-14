package org.baebe.coffeetrading.api.product.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.baebe.coffeetrading.api.product.business.ProductBusiness;
import org.baebe.coffeetrading.api.product.dto.response.ProductOptionResponse;
import org.baebe.coffeetrading.api.product.dto.response.ProductResponse;
import org.baebe.coffeetrading.commons.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductBusiness productBusiness;

    @GetMapping
    public ApiResponse<List<ProductResponse>> getProducts(@RequestParam String storeId) {
        return ApiResponse.successByData((productBusiness.getAllProduct(storeId)));
    }

    @GetMapping("/option")
    public ApiResponse<List<ProductOptionResponse>> getProductOptions(@RequestParam String productId) {
        return ApiResponse.successByData((productBusiness.getProductOptions(productId)));
    }
}
