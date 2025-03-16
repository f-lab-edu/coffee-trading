package org.baebe.coffeetrading.domains.product.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.baebe.coffeetrading.domains.common.FileBaseEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class ProductImagesEntity extends FileBaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private Products product;
}
