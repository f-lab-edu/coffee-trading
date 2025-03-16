package org.baebe.coffeetrading.domains.review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FLAVOR_ITEMS")
public class FlavorItems extends FlavorItemsEntity{

    private FlavorItems(
        String reviewItemName
    ) {
        this.setReviewItemName(reviewItemName);
    }
}
