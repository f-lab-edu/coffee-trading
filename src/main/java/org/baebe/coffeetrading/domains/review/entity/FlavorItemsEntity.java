package org.baebe.coffeetrading.domains.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.domains.common.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "FLAVOR_ITEMS")
public class FlavorItemsEntity extends BaseTimeEntity {

    @Column(name = "NAME")
    private String reviewItemName;

    @Builder
    private FlavorItemsEntity(String reviewItemName) {
        this.reviewItemName = reviewItemName;
    }
}
