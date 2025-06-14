package org.baebe.coffeetrading.api.store.business;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.baebe.coffeetrading.api.store.dto.request.AddStoreRequest;
import org.baebe.coffeetrading.api.store.dto.request.ModifyStoreRequest;
import org.baebe.coffeetrading.api.store.dto.request.StoreRequest;
import org.baebe.coffeetrading.api.store.dto.response.StoreResponse;
import org.baebe.coffeetrading.commons.exception.common.CoreException;
import org.baebe.coffeetrading.commons.types.exception.ErrorTypes;
import org.baebe.coffeetrading.domains.store.entity.StoresEntity;
import org.baebe.coffeetrading.domains.store.service.StoresService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreBusiness {
    private final StoresService storeService;

    public Page<StoreResponse> getStores(StoreRequest request) {
        Pageable pageable = PageRequest.of(
            request.getPage(),
            request.getSize(),
            Sort.by(Sort.Direction.fromString(request.getSortDirection()), request.getSortBy())
        );

        Page<StoresEntity> storesPage = storeService.searchStores(
            request.getTitle(),
            request.getAddress(),
            request.getStoreType(),
            request.getStatus(),
            pageable
        );

        return storesPage.map(StoreResponse::of);
    }

    @Transactional
    public StoreResponse addStore(AddStoreRequest request) {
        checkDuplicateStore(request.getTitle(), request.getAddress());

        StoresEntity store = StoresEntity.builder()
            .title(request.getTitle())
            .address(request.getAddress())
            .roadNameAddress(request.getRoadNameAddress())
            .telephone(request.getTelephone())
            .description(request.getDescription())
            .status(request.getStatus())
            .storeType(request.getStoreType())
            .build();

        return StoreResponse.of(storeService.saveStore(store));
    }

    // 상호명과 주소가 동일할 시 같은 가게로 판단한다.
    private void checkDuplicateStore(String title, String address) {
        if (storeService.existsByTitleAndAddress(title, address)) {
            throw new CoreException(ErrorTypes.DUPLICATE_STORE);
        }
    }

    public List<StoreResponse> getAllStores() {
        return storeService.getAllStores().stream()
            .map(StoreResponse::of)
            .collect(Collectors.toList());
    }

    @Transactional
    public StoreResponse modifyStore(ModifyStoreRequest request) {
        StoresEntity store = storeService.getStoreById(Long.valueOf(request.getStoreId()));
        store.update(
            request.getTitle(),
            request.getAddress(),
            request.getRoadNameAddress(),
            request.getTelephone(),
            request.getDescription(),
            request.getStatus(),
            request.getStoreType()
        );

        return StoreResponse.of(storeService.saveStore(store));
    }

    @Transactional
    public void deleteStore(Long storeId) {
        StoresEntity store = storeService.getStoreById(storeId);
        storeService.deleteStore(store);
    }
}
