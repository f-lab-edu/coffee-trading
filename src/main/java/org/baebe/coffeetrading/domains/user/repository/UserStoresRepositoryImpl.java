package org.baebe.coffeetrading.domains.user.repository;

import static org.baebe.coffeetrading.domains.user.entity.QUserStoresEntity.userStoresEntity;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.baebe.coffeetrading.api.user.dto.response.MyStoreInfoResponse;

@RequiredArgsConstructor
public class UserStoresRepositoryImpl implements UserStoresRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<MyStoreInfoResponse> getMyStoreInfoByGroupId(Long groupId) {


        return queryFactory
            .select(Projections.constructor(MyStoreInfoResponse.class,
                userStoresEntity.stores.id.stringValue(),
                userStoresEntity.stores.title,
                userStoresEntity.stores.roadNameAddress))
            .from(userStoresEntity)
            .join(userStoresEntity.stores).fetchJoin()
            .where(userStoresEntity.userStoreGroup.id.eq(groupId))
            .fetch();

    }
}
