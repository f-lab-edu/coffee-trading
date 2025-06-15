package org.baebe.coffeetrading.domains.product.repository;

import java.util.List;
import org.baebe.coffeetrading.domains.product.entity.ProductImagesEntity;

public interface ProductImagesRepositoryCustom {

    List<ProductImagesEntity> getImagesByProductId(Long productId);
}
