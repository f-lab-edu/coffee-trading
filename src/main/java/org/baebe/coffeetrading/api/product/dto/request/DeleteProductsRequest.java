package org.baebe.coffeetrading.api.product.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DeleteProductsRequest {

    private String productId;

    @Builder
    public DeleteProductsRequest(String productId) {
        this.productId = productId;
    }
}
