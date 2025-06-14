package org.baebe.coffeetrading.api.store.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.baebe.coffeetrading.domains.store.entity.StoresEntity;

@Getter
public class StoreResponse {
    private String storeId;
    private String title;
    private String address;
    private String roadNameAddress;
    private String telephone;
    private String description;
    private String status;
    private String storeType;

    public static StoreResponse of(StoresEntity store) {
        return StoreResponse.builder()
            .storeId(store.getId().toString())
            .title(store.getTitle())
            .address(store.getAddress())
            .roadNameAddress(store.getRoadNameAddress())
            .telephone(store.getTelephone())
            .description(store.getDescription())
            .status(store.getStatus().getValue())
            .storeType(store.getStoreType().getValue())
            .build();
    }

    @Builder
    public StoreResponse(
        String storeId,
        String title,
        String address,
        String roadNameAddress,
        String telephone,
        String description,
        String status,
        String storeType
    ) {
        this.storeId = storeId;
        this.title = title;
        this.address = address;
        this.roadNameAddress = roadNameAddress;
        this.telephone = telephone;
        this.description = description;
        this.status = status;
        this.storeType = storeType;
    }
}