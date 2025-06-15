package org.baebe.coffeetrading.api.product.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.baebe.coffeetrading.api.product.business.ProductBusiness;
import org.baebe.coffeetrading.api.product.dto.request.AddProductImageRequest;
import org.baebe.coffeetrading.api.product.dto.request.AddProductOptionRequest;
import org.baebe.coffeetrading.api.product.dto.request.AddProductRequest;
import org.baebe.coffeetrading.api.product.dto.request.DeleteProductImageRequest;
import org.baebe.coffeetrading.api.product.dto.request.DeleteProductOptionsRequest;
import org.baebe.coffeetrading.api.product.dto.request.DeleteProductsRequest;
import org.baebe.coffeetrading.api.product.dto.request.ModifyProductOptionRequest;
import org.baebe.coffeetrading.api.product.dto.request.ModifyProductRequest;
import org.baebe.coffeetrading.api.product.dto.response.ProductOptionResponse;
import org.baebe.coffeetrading.api.product.dto.response.ProductResponse;
import org.baebe.coffeetrading.commons.dto.response.ApiResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/partner-api/product")
public class PartnerProductController {

    private final ProductBusiness productBusiness;

    @PostMapping
    public ApiResponse<ProductResponse> addProduct(@RequestBody AddProductRequest request) {
        return ApiResponse.successByData(productBusiness.addProduct(request));
    }

    @PutMapping()
    public ApiResponse<ProductResponse> modifyProduct(@RequestBody ModifyProductRequest request) {
        return ApiResponse.successByData(productBusiness.modifyProduct(request));
    }

    @DeleteMapping()
    public ApiResponse<Void> deleteProduct(@RequestBody DeleteProductsRequest request) {
        productBusiness.deleteProduct(request);
        return ApiResponse.successByEmpty();
    }

    @PostMapping("/option")
    public ApiResponse<ProductOptionResponse> addProductOption(@RequestBody AddProductOptionRequest request) {
        return ApiResponse.successByData(productBusiness.addProductOption(request));
    }

    @PutMapping("/option")
    public ApiResponse<ProductOptionResponse> modifyProductOption(@RequestBody ModifyProductOptionRequest request) {
        return ApiResponse.successByData(productBusiness.modifyProductOption(request));
    }

    @DeleteMapping("/option")
    public ApiResponse<Void> deleteProductOption(@RequestBody DeleteProductOptionsRequest request) {
        productBusiness.deleteProductOption(request);
        return ApiResponse.successByEmpty();
    }

    @PostMapping("/image")
    public ApiResponse<Void> addProductImage(@RequestPart("file") MultipartFile file,
        @RequestPart("request") AddProductImageRequest request) {

        productBusiness.addProductImage(file, request);
        return ApiResponse.successByEmpty();
    }

    @PostMapping("/images")
    public ApiResponse<Void> addProductImages(@RequestPart("files")List<MultipartFile> files,
    @RequestPart("request") AddProductImageRequest request) {
        productBusiness.addProductImages(files, request);
        return ApiResponse.successByEmpty();
    }

    @DeleteMapping("/images")
    public ApiResponse<Void> deleteProductImages(@RequestBody DeleteProductImageRequest request) {
        productBusiness.deleteProductImage(request);
        return ApiResponse.successByEmpty();
    }
} 