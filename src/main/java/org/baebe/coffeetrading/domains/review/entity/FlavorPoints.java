package org.baebe.coffeetrading.domains.review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.domains.store.entity.Stores;
import org.baebe.coffeetrading.domains.user.entity.Users;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FLAVOR_POINTS")
public class FlavorPoints extends FlavorPointsEntity{

    private FlavorPoints(
        Integer reviewPoint,
        Stores store,
        FlavorItems flavorItem,
        Users user
    ) {
        this.setReviewPoint(reviewPoint);
        this.setStore(store);
        this.setFlavorItem(flavorItem);
        this.setUser(user);
    }
}
