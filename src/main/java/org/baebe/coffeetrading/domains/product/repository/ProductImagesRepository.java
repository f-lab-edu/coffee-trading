package org.baebe.coffeetrading.domains.product.repository;

import org.baebe.coffeetrading.domains.product.entity.ProductImagesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImagesRepository extends JpaRepository<ProductImagesEntity, Long>, ProductImagesRepositoryCustom {

    void deleteByProductId(Long productId);
} 