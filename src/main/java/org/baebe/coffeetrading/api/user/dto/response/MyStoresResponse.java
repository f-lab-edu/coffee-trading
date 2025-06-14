package org.baebe.coffeetrading.api.user.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyStoresResponse {

    String storeGroupId;
    String storeListName;

    @Builder
    public MyStoresResponse(String storeGroupId, String storeListName) {
        this.storeGroupId = storeGroupId;
        this.storeListName = storeListName;
    }
}
