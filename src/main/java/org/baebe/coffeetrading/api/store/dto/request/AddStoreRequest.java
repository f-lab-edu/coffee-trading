package org.baebe.coffeetrading.api.store.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.baebe.coffeetrading.commons.types.store.StoreStatus;
import org.baebe.coffeetrading.commons.types.store.StoreTypes;

@Getter
public class AddStoreRequest {
    private String title;
    private String address;
    private String roadNameAddress;
    private String telephone;
    private String description;
    private StoreStatus status;
    private StoreTypes storeType;

    @Builder
    public AddStoreRequest(
        String title,
        String address,
        String roadNameAddress,
        String telephone,
        String description,
        StoreStatus status,
        StoreTypes storeType
    ) {
        this.title = title;
        this.address = address;
        this.roadNameAddress = roadNameAddress;
        this.telephone = telephone;
        this.description = description;
        this.status = status;
        this.storeType = storeType;
    }
}