package org.baebe.coffeetrading.domains.store.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.domains.store.entity.StoresEntity;
import org.baebe.coffeetrading.domains.store.repository.StoresRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class StoresService {

    private final StoresRepository storesRepository;

    public StoresEntity getStoreById(Long storeId) {
        return storesRepository.findById(storeId)
            .orElseThrow(() -> new CoreException(ErrorTypes.STORE_NOT_FOUND));
    }

    public boolean existsByTitleAndAddress(String title, String address) {
        return storesRepository.existsByTitleIgnoreCaseAndAddressIgnoreCase(title, address);
    }

    @Transactional
    public StoresEntity saveStore(StoresEntity store) {
        return storesRepository.save(store);
    }

    public List<StoresEntity> getAllStores() {
        return storesRepository.findAll();
    }

    @Transactional
    public void deleteStore(StoresEntity store) {
        storesRepository.delete(store);
    }

    public Page<StoresEntity> searchStores(String title, String address, String storeType, String status, Pageable pageable) {
        return storesRepository.getStoresBytitleAndAddressAndStoreTypeAndStatus(title, address, storeType, status, pageable);
    }
}
