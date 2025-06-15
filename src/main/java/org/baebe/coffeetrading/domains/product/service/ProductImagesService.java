package org.baebe.coffeetrading.domains.product.service;

import static org.baebe.coffeetrading.commons.types.exception.ErrorTypes.FILE_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.domains.product.entity.ProductImagesEntity;
import org.baebe.coffeetrading.domains.product.repository.ProductImagesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductImagesService {

    private final ProductImagesRepository productImagesRepository;

    public List<ProductImagesEntity> getProductImagesByProductId(Long productId) {
        return productImagesRepository.getImagesByProductId(productId);
    }

    @Transactional
    public ProductImagesEntity saveProductImage(ProductImagesEntity productImage) {
        return productImagesRepository.save(productImage);
    }

    @Transactional
    public void saveProductImages(List<ProductImagesEntity> productImages) {
        productImagesRepository.saveAll(productImages);
    }

    @Transactional
    public void deleteProductImages(Long productId) {
        productImagesRepository.deleteByProductId(productId);
    }

    @Transactional
    public void deleteProductImagesById(Long productImagesId) {
        productImagesRepository.deleteById(productImagesId);
    }
} 