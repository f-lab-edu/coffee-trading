package org.baebe.coffeetrading.domains.product.repository;

import static org.baebe.coffeetrading.domains.product.entity.QProductImagesEntity.productImagesEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.baebe.coffeetrading.domains.product.entity.ProductImagesEntity;

@RequiredArgsConstructor
public class ProductImagesRepositoryImpl implements ProductImagesRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductImagesEntity> getImagesByProductId(Long productId) {

        return queryFactory
            .selectFrom(productImagesEntity)
            .where(productImagesEntity.product.id.eq(productId))
            .fetch();
    }
}
