package org.baebe.coffeetrading.api.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.baebe.coffeetrading.commons.types.product.ProductStatus;

@Getter
public class ModifyProductOptionRequest {

    private String productOptionsId;
    private String name;
    private String description;
    private int price;
    private ProductStatus status;

    @Builder
    public ModifyProductOptionRequest(
        String productOptionsId,
        String name,
        String description,
        int price,
        ProductStatus status
    ) {
        this.productOptionsId = productOptionsId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
    }
}
