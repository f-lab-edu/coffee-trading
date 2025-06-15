package org.baebe.coffeetrading.api.product.dto.response;

import lombok.Builder;
import lombok.Getter;
import org.baebe.coffeetrading.commons.types.product.ProductStatus;
import org.baebe.coffeetrading.domains.product.entity.ProductOptionsEntity;

@Getter
public class ProductOptionResponse {

    private String productOptionsId;
    private String name;
    private String description;
    private int price;
    private ProductStatus status;

    @Builder
    public ProductOptionResponse(
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

    public static ProductOptionResponse of(ProductOptionsEntity ent) {
        return ProductOptionResponse.builder()
            .productOptionsId(ent.getId().toString())
            .name(ent.getName())
            .description(ent.getDescription())
            .price(ent.getPrice())
            .status(ent.getStatus())
            .build();
    }
}
