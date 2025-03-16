package org.baebe.coffeetrading.domains.store.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.baebe.coffeetrading.commons.types.store.StoreTypes;
import org.baebe.coffeetrading.domains.common.BaseCreatedAtEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class StoresEntity extends BaseCreatedAtEntity {

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
}