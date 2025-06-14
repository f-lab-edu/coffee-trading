package org.baebe.coffeetrading.api.product.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import org.baebe.coffeetrading.commons.types.product.ProductStatus;
import org.baebe.coffeetrading.commons.types.product.ProductType;

@Getter
public class AddProductRequest {
    private String storeId;
    private String name;
    private String description;
    private int price;
    private ProductType type;
    private ProductStatus status;

    @Builder
    public AddProductRequest(
        String storeId,
        String name,
        String description,
        int price,
        ProductType type,
        ProductStatus status
    ) {
        this.storeId = storeId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.status = status;
    }
}