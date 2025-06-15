package org.baebe.coffeetrading.api.product.business;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
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
import org.baebe.coffeetrading.api.product.dto.response.ProductResponse.ProductImage;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.commons.utils.file.FileUtils;
import org.baebe.coffeetrading.domains.product.entity.ProductImagesEntity;
import org.baebe.coffeetrading.domains.product.entity.ProductOptionsEntity;
import org.baebe.coffeetrading.domains.product.entity.ProductsEntity;
import org.baebe.coffeetrading.domains.product.service.ProductImagesService;
import org.baebe.coffeetrading.domains.product.service.ProductOptionsService;
import org.baebe.coffeetrading.domains.product.service.ProductsService;
import org.baebe.coffeetrading.domains.store.entity.StoresEntity;
import org.baebe.coffeetrading.domains.store.service.StoresService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProductBusiness {

    @Value("${spring.servlet.multipart.location}")
    private String location;
    private final ProductsService productsService;
    private final StoresService storesService;
    private final ProductImagesService productImagesService;
    private final ProductOptionsService productOptionsService;

    public List<ProductResponse> getAllProduct(String storeId) {

        return productsService.getProductByStoreId(Long.parseLong(storeId)).stream()
            .map(product -> {

                List<ProductImagesEntity> productImagesEntList =
                    productImagesService.getProductImagesByProductId(product.getId());

                List<ProductImage> images = convertToProductImage(productImagesEntList);

                return ProductResponse.of(product, images);
            }).toList();
    }

    @Transactional
    public ProductResponse addProduct(AddProductRequest request) {

        checkDuplicateProduct(request.getStoreId(), request.getName());
        StoresEntity store = storesService.getStoreById(Long.parseLong(request.getStoreId()));

        ProductsEntity product = ProductsEntity.builder()
            .name(request.getName())
            .description(request.getDescription())
            .price(request.getPrice())
            .status(request.getStatus())
            .store(store)
            .build();

        return ProductResponse.of(productsService.saveProduct(product));
    }

    @Transactional
    public ProductResponse modifyProduct(ModifyProductRequest request) {

        ProductsEntity product = productsService.getProductById(Long.parseLong(request.getProductId()));

        product.update(
            request.getName(),
            request.getDescription(),
            request.getPrice(),
            request.getType(),
            request.getStatus()
        );

        return ProductResponse.of(productsService.saveProduct(product));
    }

    @Transactional
    public void deleteProduct(DeleteProductsRequest request) {

        Long productId = Long.valueOf(request.getProductId());

        ProductsEntity product =
            productsService.getProductById(productId);

        productImagesService.deleteProductImages(productId);
        productOptionsService.deleteProductOptionsByProductId(productId);
        productsService.deleteProduct(product);
    }

    public List<ProductOptionResponse> getProductOptions(String productId) {

        return productOptionsService.getProductOptionsByProductId(Long.parseLong(productId)).stream()
            .map(productOption ->
                ProductOptionResponse.builder()
                    .productOptionsId(productOption.getId().toString())
                    .name(productOption.getName())
                    .description(productOption.getDescription())
                    .price(productOption.getPrice())
                    .status(productOption.getStatus())
                    .build()).toList();
    }

    @Transactional
    public ProductOptionResponse addProductOption(AddProductOptionRequest request) {

        checkDuplicateProductOption(request.getProductId(), request.getName());
        ProductsEntity product =
            productsService.getProductById(Long.parseLong(request.getProductId()));

        ProductOptionsEntity productOption = ProductOptionsEntity.builder()
            .name(request.getName())
            .description(request.getDescription())
            .price(request.getPrice())
            .status(request.getStatus())
            .product(product)
            .build();

        return ProductOptionResponse.of(productOptionsService.saveProductOptions(productOption));
    }

    @Transactional
    public ProductOptionResponse modifyProductOption(ModifyProductOptionRequest request) {

        ProductOptionsEntity productOption =
            productOptionsService.getProductOptionsById(Long.parseLong(request.getProductOptionsId()));

        productOption.update(
            request.getName(),
            request.getDescription(),
            request.getPrice(),
            request.getStatus()
        );

        return ProductOptionResponse.of(productOptionsService.saveProductOptions(productOption));
    }

    public void deleteProductOption(DeleteProductOptionsRequest request) {
        productOptionsService.deleteProductOptionsById(Long.valueOf(request.getProductOptionsId()));
    }

    @Transactional
    public void addProductImage(MultipartFile file, AddProductImageRequest request) {

        FileUtils.saveFile(file);

        ProductsEntity product = productsService.getProductById(Long.valueOf(request.getProductId()));
        String encryptedFileName = FileUtils.fileNameToUUID(file.getOriginalFilename());

        ProductImagesEntity productImagesEntity = ProductImagesEntity.of(product, encryptedFileName,
            location, file.getOriginalFilename());

        productImagesService.saveProductImage(productImagesEntity);
    }

    @Transactional
    public void addProductImages(List<MultipartFile> files, AddProductImageRequest request) {

        List<ProductImagesEntity> productImagesEntityList = new ArrayList<>();

        files.forEach(file -> {
            FileUtils.saveFile(file);

            ProductsEntity product = productsService.getProductById(Long.valueOf(request.getProductId()));
            String encryptedFileName = FileUtils.fileNameToUUID(file.getOriginalFilename());

            ProductImagesEntity productImagesEntity = ProductImagesEntity.of(product, encryptedFileName,
                location, file.getOriginalFilename());

            productImagesEntityList.add(productImagesEntity);
        });

        productImagesService.saveProductImages(productImagesEntityList);
    }

    public void deleteProductImage(DeleteProductImageRequest request) {
        productImagesService.deleteProductImagesById(Long.valueOf(request.getProductImagesId()));
    }

    private void checkDuplicateProduct(String storeId, String name) {
        if (productsService.existsByStoreIdAndName(Long.parseLong(storeId), name)) {
            throw new CoreException(ErrorTypes.DUPLICATE_PRODUCT);
        }
    }

    private void checkDuplicateProductOption(String productId, String name) {
        if (productOptionsService.existsByProductIdAndName(Long.parseLong(productId), name)) {
            throw new CoreException(ErrorTypes.DUPLICATE_PRODUCT_OPTION);
        }
    }

    private List<ProductResponse.ProductImage> convertToProductImage(List<ProductImagesEntity> images) {

        return images.stream().map(image -> {
            String url = constructImageUrl(image.getFilePath(), image.getEncryptedFileName());
            String contentType = getContentType(image.getFilePath(), image.getEncryptedFileName());

            return ProductResponse.ProductImage
                .builder()
                .id(image.getId().toString())
                .url(url)
                .contentType(contentType)
                .build();
        }).toList();
    }

    private String constructImageUrl(String filePath, String encryptedFileName) {
        return location + filePath + encryptedFileName;
    }

    private String getContentType(String filePath, String encryptedFileName) {

        Path path = Paths.get(filePath, encryptedFileName);

        try {
            return Files.probeContentType(path);
        } catch (IOException ignored) {
            throw new CoreException(ErrorTypes.FILE_NOT_FOUND);
        }
    }
} 