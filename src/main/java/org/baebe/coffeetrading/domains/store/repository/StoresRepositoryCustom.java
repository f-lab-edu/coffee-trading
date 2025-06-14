package org.baebe.coffeetrading.domains.store.repository;

import org.baebe.coffeetrading.domains.store.entity.StoresEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoresRepositoryCustom {
    Page<StoresEntity> getStoresBytitleAndAddressAndStoreTypeAndStatus(
        String title,
        String address,
        String storeType,
        String status,
        Pageable pageable
    );
}
