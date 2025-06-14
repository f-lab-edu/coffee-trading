package org.baebe.coffeetrading.domains.product.service;

import static org.baebe.coffeetrading.commons.types.exception.ErrorTypes.PRODUCT_NOT_FOUND;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.domains.product.entity.ProductsEntity;
import org.baebe.coffeetrading.domains.product.repository.ProductsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductsService {

    private final ProductsRepository productsRepository;

    public List<ProductsEntity> getProductByStoreId(Long storeId) {
        return productsRepository.getAllProductsByStoreId(storeId);
    }

    public ProductsEntity getProductById(Long productId) {
        return productsRepository.findById(productId)
            .orElseThrow(() -> new CoreException(PRODUCT_NOT_FOUND));
    }

    public boolean existsByStoreIdAndName(Long storeId, String name) {
        return productsRepository.existsByStoreIdAndNameIgnoreCase(storeId, name);
    }

    @Transactional
    public ProductsEntity saveProduct(ProductsEntity product) {
        return productsRepository.save(product);
    }

    @Transactional
    public void deleteProduct(ProductsEntity product) {
        productsRepository.delete(product);
    }
} 