package org.baebe.coffeetrading.domains.product.repository;

import org.baebe.coffeetrading.domains.product.entity.ProductOptionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductOptionsRepository extends JpaRepository<ProductOptionsEntity, Long> {
    List<ProductOptionsEntity> findByProductId(Long productId);
    boolean existsByProductIdAndNameIgnoreCase(Long productId, String productOptionName);

    @Modifying
    @Query("DELETE FROM ProductOptionsEntity s WHERE s.product.id = :productId")
    void deleteByProductId(@Param("productId") Long productId);
} 