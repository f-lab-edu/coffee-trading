package org.baebe.coffeetrading.domains.product.repository;

import java.util.List;
import org.baebe.coffeetrading.domains.product.entity.ProductsEntity;

public interface ProductsRepositoryCustom {

    List<ProductsEntity> getAllProductsByStoreId(Long storeID);
} 