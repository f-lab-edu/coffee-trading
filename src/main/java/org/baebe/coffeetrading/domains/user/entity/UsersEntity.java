package org.baebe.coffeetrading.domains.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.baebe.coffeetrading.commons.types.user.AccountTypes;
import org.baebe.coffeetrading.commons.types.user.GenderTypes;
import org.baebe.coffeetrading.commons.types.user.UserStatus;
import org.baebe.coffeetrading.commons.types.user.UserRole;
import org.baebe.coffeetrading.domains.common.BaseCreatedAtEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class UsersEntity extends BaseCreatedAtEntity {

    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    @Column(name = "PASSWORD", nullable = false, length = 256)
    private String password;

    @Column(name = "USER_NAME", nullable = false, length = 256)
    private String userName;

    @Column(name = "PHONE", length = 256)
    private String phone;

    @Column(name = "BIRTHDAY", length = 10)
    private String birthday;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private GenderTypes gender;

    @Column(name = "NICKNAME", nullable = false, length = 30)
    private String nickname;

    @Column(name = "STATUS", nullable = false)
    private UserStatus status;

    @Column(name = "ACCOUNT_TYPE")
    @Enumerated(EnumType.STRING)
    private AccountTypes accountType;

    @Column(name = "USER_TYPE")
    @Enumerated(EnumType.STRING)
    private UserRole userType;
}
