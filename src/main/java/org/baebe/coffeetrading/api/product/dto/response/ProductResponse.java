package org.baebe.coffeetrading.api.product.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import org.baebe.coffeetrading.commons.types.product.ProductStatus;
import org.baebe.coffeetrading.commons.types.product.ProductType;
import org.baebe.coffeetrading.domains.product.entity.ProductsEntity;

@Getter
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private int price;
    private ProductStatus status;
    private ProductType type;
    private String createdAt;
    private String updatedAt;
    private List<ProductImage> images;

    @Builder
    public ProductResponse(
        String id,
        String name,
        String description,
        int price,
        ProductStatus status,
        ProductType type,
        String createdAt,
        String updatedAt,
        List<ProductImage> images
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.images = images;
    }

    @Getter
    public static class ProductImage {
        private String id;
        private String url;
        private String contentType;

        @Builder
        public ProductImage(String id, String url, String contentType) {
            this.id = id;
            this.url = url;
            this.contentType = contentType;
        }
    }

    public static ProductResponse of (ProductsEntity product, List<ProductImage> images) {
        return ProductResponse.builder()
            .id(product.getId().toString())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .status(product.getStatus())
            .createdAt(product.getCreatedAt().toString())
            .updatedAt(product.getUpdatedAt().toString())
            .images(images)
            .build();
    }

    public static ProductResponse of (ProductsEntity product) {
        return ProductResponse.builder()
            .id(product.getId().toString())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())
            .status(product.getStatus())
            .createdAt(product.getCreatedAt().toString())
            .updatedAt(product.getUpdatedAt().toString())
            .build();
    }
}