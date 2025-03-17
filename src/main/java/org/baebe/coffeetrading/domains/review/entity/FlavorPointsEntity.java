package org.baebe.coffeetrading.domains.review.entity;

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
import org.baebe.coffeetrading.domains.common.BaseCreatedAtEntity;
import org.baebe.coffeetrading.domains.store.entity.StoresEntity;
import org.baebe.coffeetrading.domains.user.entity.UsersEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FLAVOR_POINTS")
public class FlavorPointsEntity extends BaseCreatedAtEntity {

    @Column(name = "POINT", nullable = false)
    private Integer reviewPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STORE_ID")
    private StoresEntity store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FLAVORITEM_ID")
    private FlavorItemsEntity flavorItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UsersEntity user;

    @Builder
    private FlavorPointsEntity(
        Integer reviewPoint,
        StoresEntity store,
        FlavorItemsEntity flavorItem,
        UsersEntity user
    ) {
        this.reviewPoint = reviewPoint;
        this.store = store;
        this.flavorItem = flavorItem;
        this.user = user;
    }
}
