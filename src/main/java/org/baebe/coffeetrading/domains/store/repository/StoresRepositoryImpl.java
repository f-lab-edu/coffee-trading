package org.baebe.coffeetrading.domains.store.repository;

import static org.baebe.coffeetrading.domains.store.entity.QStoresEntity.storesEntity;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.baebe.coffeetrading.commons.types.store.StoreStatus;
import org.baebe.coffeetrading.commons.types.store.StoreTypes;
import org.baebe.coffeetrading.domains.store.entity.QStoresEntity;
import org.baebe.coffeetrading.domains.store.entity.StoresEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class StoresRepositoryImpl implements StoresRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<StoresEntity> getStoresBytitleAndAddressAndStoreTypeAndStatus(
        String title,
        String address,
        String storeType,
        String status,
        Pageable pageable
    ) {

        List<BooleanExpression> conditions = new ArrayList<>();

        if (StringUtils.hasText(title)) {
            conditions.add(storesEntity.title.containsIgnoreCase(title));
        }
        if (StringUtils.hasText(address)) {
            conditions.add(storesEntity.address.containsIgnoreCase(address));
        }
        if (StringUtils.hasText(storeType)) {
            conditions.add(storesEntity.storeType.eq(StoreTypes.valueOf(storeType)));
        }
        if (StringUtils.hasText(status)) {
            conditions.add(storesEntity.status.eq(StoreStatus.valueOf(status)));
        }

        BooleanExpression finalCondition = conditions.stream()
            .reduce(BooleanExpression::and)
            .orElse(null);

        List<StoresEntity> results = queryFactory
            .selectFrom(storesEntity)
            .where(finalCondition)
            .orderBy(getOrderSpecifiers(pageable.getSort()))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Long total = queryFactory
            .select(storesEntity.count())
            .from(storesEntity)
            .where(finalCondition)
            .fetchOne();

        return new PageImpl<>(results, pageable, total != null ? total : 0L);
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Sort sort) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
        QStoresEntity stores = storesEntity;

        if (sort != null && sort.isSorted()) {
            sort.forEach(order -> {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                String property = order.getProperty();

                switch (property) {
                    case "title":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, stores.title));
                        break;
                    case "address":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, stores.address));
                        break;
                    case "storeType":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, stores.storeType));
                        break;
                    case "status":
                        orderSpecifiers.add(new OrderSpecifier<>(direction, stores.status));
                        break;
                    default:
                        orderSpecifiers.add(new OrderSpecifier<>(Order.DESC, stores.createdAt));
                }
            });
        } else {
            orderSpecifiers.add(new OrderSpecifier<>(Order.DESC, stores.createdAt));
        }

        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }
}
