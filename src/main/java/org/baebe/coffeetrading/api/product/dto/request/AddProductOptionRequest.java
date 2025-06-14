package org.baebe.coffeetrading.api.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.baebe.coffeetrading.commons.types.product.ProductStatus;

@Getter
public class AddProductOptionRequest {

    private String productId;
    private String name;
    private String description;
    private int price;
    private ProductStatus status;

    @Builder
    public AddProductOptionRequest(
        String productId,
        String name,
        String description,
        int price,
        ProductStatus status
    ) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
    }
}
