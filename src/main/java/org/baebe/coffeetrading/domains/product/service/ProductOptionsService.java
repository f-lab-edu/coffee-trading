package org.baebe.coffeetrading.domains.product.service;

import static org.baebe.coffeetrading.commons.types.exception.ErrorTypes.PRODUCT_OPTION_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.domains.product.entity.ProductOptionsEntity;
import org.baebe.coffeetrading.domains.product.repository.ProductOptionsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductOptionsService {

    private final ProductOptionsRepository productOptionsRepository;

    public ProductOptionsEntity getProductOptionsById(Long productOptionsId) {
        return productOptionsRepository.findById(productOptionsId)
            .orElseThrow(() -> new CoreException(PRODUCT_OPTION_NOT_FOUND));
    }

    public List<ProductOptionsEntity> getProductOptionsByProductId(Long productId) {
        return productOptionsRepository.findByProductId(productId);
    }

    @Transactional
    public ProductOptionsEntity saveProductOptions(ProductOptionsEntity productOption) {
        return productOptionsRepository.save(productOption);
    }

    @Transactional
    public void deleteProductOptionsById(Long productOptionsId) {
        productOptionsRepository.deleteById(productOptionsId);
    }

    @Transactional
    public void deleteProductOptionsByProductId(Long productId) {
        productOptionsRepository.deleteByProductId(productId);
    }

    public boolean existsByProductIdAndName(Long productId, String name) {
        return productOptionsRepository.existsByProductIdAndNameIgnoreCase(productId, name);
    }
} 