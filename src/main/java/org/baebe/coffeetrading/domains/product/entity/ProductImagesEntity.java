package org.baebe.coffeetrading.domains.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.domains.common.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "PRODUCT_IMAGES")
public class ProductImagesEntity extends BaseTimeEntity {

    @Column(name = "ORIGINAL_FILE_NAME", nullable = false)
    private String originalFileName;

    @Column(name = "ENCRYPTED_FILE_NAME", nullable = false)
    private String encryptedFileName;

    @Column(name = "FILE_PATH", nullable = false)
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    private ProductsEntity product;

    @Builder
    private ProductImagesEntity(
        String originalFileName,
        String encryptedFileName,
        String filePath,
        ProductsEntity product
    ) {
        this.originalFileName = originalFileName;
        this.encryptedFileName = encryptedFileName;
        this.filePath = filePath;
        this.product = product;
    }

    public static ProductImagesEntity of(
        ProductsEntity product,
        String originalFileName,
        String encryptedFileName,
        String filePath
    ) {
        return ProductImagesEntity.builder()
            .originalFileName(originalFileName)
            .encryptedFileName(encryptedFileName)
            .filePath(filePath)
            .product(product)
            .build();
    }
}
