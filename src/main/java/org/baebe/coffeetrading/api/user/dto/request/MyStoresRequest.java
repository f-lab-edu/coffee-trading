package org.baebe.coffeetrading.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MyStoresRequest {

    @NotBlank(message = "가게를 추가할 나만의 가게 그룹을 선택해주세요.")
    private String userStoreGroupId;
    @NotBlank(message = "가게 정보가 확인되지 않습니다.")
    private String storeId;

    @Builder
    public MyStoresRequest(String userStoreGroupId, String storeId) {
        this.userStoreGroupId = userStoreGroupId;
        this.storeId = storeId;
    }
}
