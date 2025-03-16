package org.baebe.coffeetrading.domains.store.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.domains.product.entity.Products;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "STORES")
public class Stores extends StoresEntity {

    @Builder
    private Stores(
        String title,
        String address,
        String roadNameAddress,
        String telephone,
        String description
    ) {
        this.setTitle(title);
        this.setAddress(address);
        this.setRoadNameAddress(roadNameAddress);
        this.setTelephone(telephone);
        this.setDescription(description);
    }
}
