package org.baebe.coffeetrading.domains.review.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.baebe.coffeetrading.domains.common.BaseCreatedAtEntity;
import org.baebe.coffeetrading.domains.store.entity.Stores;
import org.baebe.coffeetrading.domains.user.entity.Users;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class FlavorPointsEntity extends BaseCreatedAtEntity {

    @Column(name = "POINT", nullable = false)
    private Integer reviewPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private Stores store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private FlavorItems flavorItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private Users user;
}
