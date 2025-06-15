package org.baebe.coffeetrading.domains.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.domains.common.BaseTimeEntity;
import org.baebe.coffeetrading.domains.store.entity.StoresEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "USER_STORES")
public class UserStoresEntity extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_STORE_GROUPS_ID")
    private UserStoreGroupsEntity userStoreGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORES_ID")
    private StoresEntity stores;

    @Builder
    private UserStoresEntity(
        UserStoreGroupsEntity userStoreGroup,
        StoresEntity stores
    ) {
        this.userStoreGroup = userStoreGroup;
        this.stores = stores;
    }

    public static UserStoresEntity of(UserStoreGroupsEntity userStoreGroup) {
        return UserStoresEntity.builder()
            .userStoreGroup(userStoreGroup)
            .stores(null)
            .build();
    }
}
