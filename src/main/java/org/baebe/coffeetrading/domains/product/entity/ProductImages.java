package org.baebe.coffeetrading.domains.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "PRODUCT_IMAGES")
public class ProductImages extends ProductImagesEntity{

    @Builder
    private ProductImages(
        String uploadFileName,
        String originalFileName,
        String fileUploadPath,
        Products product
    ) {
        this.setUploadFileName(uploadFileName);
        this.setOriginalFileName(originalFileName);
        this.setFileUploadPath(fileUploadPath);
        this.setProduct(product);
    }
}
