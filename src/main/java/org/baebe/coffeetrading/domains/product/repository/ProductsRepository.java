package org.baebe.coffeetrading.domains.product.repository;

import org.baebe.coffeetrading.domains.product.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductsRepository extends JpaRepository<ProductsEntity, Long>, ProductsRepositoryCustom {

    boolean existsByStoreIdAndNameIgnoreCase(Long storeId, String name);
}