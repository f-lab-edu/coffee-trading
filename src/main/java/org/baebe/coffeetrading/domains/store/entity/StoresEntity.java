package org.baebe.coffeetrading.domains.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.commons.types.store.StoreTypes;
import org.baebe.coffeetrading.domains.common.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "STORES")
public class StoresEntity extends BaseTimeEntity {

    @Column(name = "TITLE", nullable = false, length = 100)
    private String title;

    @Column(name = "ADDRESS", nullable = false)
    private String address;

    @Column(name = "ROAD_NAME_ADDRESS")
    private String roadNameAddress;

    @Column(name = "TELEPHONE", nullable = false)
    private String telephone;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STORE_TYPE")
    @Enumerated(EnumType.STRING)
    private StoreTypes storeType;

    @Builder
    private StoresEntity(
        String title,
        String address,
        String roadNameAddress,
        String telephone,
        String description,
        StoreTypes storeType
    ) {
        this.title = title;
        this.address = address;
        this.roadNameAddress = roadNameAddress;
        this.telephone = telephone;
        this.description = description;
        this.storeType = storeType;
    }
}