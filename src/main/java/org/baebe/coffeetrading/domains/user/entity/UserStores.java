package org.baebe.coffeetrading.domains.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.domains.store.entity.Stores;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "USER_STORES")
public class UserStores extends UserStoresEntity{

    @Builder
    private UserStores(
        String storeListName,
        Users user,
        Stores store
    ) {
        this.setStoreListName(storeListName);
        this.setUser(user);
        this.setStore(store);
    }
}
