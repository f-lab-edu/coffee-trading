package org.baebe.coffeetrading.domains.store.repository;

import org.baebe.coffeetrading.domains.store.entity.StoresEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoresRepository extends JpaRepository<StoresEntity, Long>, StoresRepositoryCustom {

    boolean existsByTitleIgnoreCaseAndAddressIgnoreCase(String title, String address);
}
