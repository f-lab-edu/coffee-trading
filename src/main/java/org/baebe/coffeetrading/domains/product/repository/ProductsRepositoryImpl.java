package org.baebe.coffeetrading.domains.product.repository;

import static org.baebe.coffeetrading.domains.product.entity.QProductsEntity.productsEntity;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.baebe.coffeetrading.domains.product.entity.ProductsEntity;

import java.util.List;

@RequiredArgsConstructor
public class ProductsRepositoryImpl implements ProductsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ProductsEntity> getAllProductsByStoreId(Long storeID) {
        return queryFactory
            .selectFrom(productsEntity)
            .where(productsEntity.store.id.eq(storeID))
            .fetch();
    }
}