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

    @Column(name = "NAME", nullable = false)
    private String storeListName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UsersEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORE_ID")
    private StoresEntity store;

    @Builder
    private UserStoresEntity(
        String storeListName,
        UsersEntity user,
        StoresEntity store
    ) {
        this.storeListName = storeListName;
        this.user = user;
        this.store = store;
    }
}
