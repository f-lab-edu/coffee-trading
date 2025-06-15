package org.baebe.coffeetrading.domains.user.repository;

import java.util.List;
import org.baebe.coffeetrading.api.user.dto.response.MyStoreInfoResponse;

public interface UserStoresRepositoryCustom {

    List<MyStoreInfoResponse> getMyStoreInfoByGroupId(Long groupId);
}
