package org.baebe.coffeetrading.api.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyStoreInfoResponse {

    private String storeId;
    private String storeName;
    private String storeAddress;

    @Builder
    public MyStoreInfoResponse(String storeId, String storeName, String storeAddress) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
    }
}
