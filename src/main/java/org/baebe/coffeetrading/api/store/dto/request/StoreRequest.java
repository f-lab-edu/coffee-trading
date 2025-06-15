package org.baebe.coffeetrading.api.store.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StoreRequest {
    private String storeType;
    private String title;
    private String address;
    private String status;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String sortDirection;

    @Builder
    public StoreRequest(
        String storeType,
        String title,
        String address,
        String status,
        Integer page,
        Integer size,
        String sortBy,
        String sortDirection
    ) {
        this.storeType = storeType;
        this.title = title;
        this.address = address;
        this.status = status;
        this.page = page;
        this.size = size;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }

    public StoreRequest() {
        this.page = 0;
        this.size = 10;
        this.sortBy = "createdAt";
        this.sortDirection = "DESC";
    }
} 