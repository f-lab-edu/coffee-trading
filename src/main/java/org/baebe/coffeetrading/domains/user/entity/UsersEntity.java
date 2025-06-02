package org.baebe.coffeetrading.domains.user.entity;

import static org.baebe.coffeetrading.commons.types.user.UserStatus.ENABLED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.baebe.coffeetrading.commons.types.user.AccountTypes;
import org.baebe.coffeetrading.commons.types.user.GenderTypes;
import org.baebe.coffeetrading.commons.types.user.UserStatus;
import org.baebe.coffeetrading.commons.types.user.UserRole;
import org.baebe.coffeetrading.domains.common.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Slf4j
@Table(name = "USERS")
public class UsersEntity extends BaseTimeEntity {

    @Column(name = "EMAIL", nullable = false, length = 50)
    private String email;

    @Column(name = "PASSWORD", length = 256)
    private String password;

    @Column(name = "USER_NAME", length = 256)
    private String userName;

    @Column(name = "PHONE", length = 256)
    private String phone;

    @Column(name = "BIRTHDAY", length = 10)
    private String birthDay;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private GenderTypes gender;

    @Column(name = "NICKNAME", nullable = false, length = 30)
    private String nickname;

    @Column(name = "STATUS", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "ACCOUNT_TYPE")
    @Enumerated(EnumType.STRING)
    private AccountTypes accountType;

    @Column(name = "USER_TYPE")
    @Enumerated(EnumType.STRING)
    private UserRole userType;

    @Builder
    private UsersEntity(
        String email,
        String password,
        String userName,
        String phone,
        String birthDay,
        GenderTypes gender,
        String nickname,
        UserStatus status,
        AccountTypes accountType,
        UserRole userType
    ) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.phone = phone;
        this.birthDay = birthDay;
        this.gender = gender;
        this.nickname = nickname;
        this.status = status;
        this.accountType = accountType;
        this.userType = userType;
    }

    public static UsersEntity ofUser(
        String email,
        String password,
        String nickname,
        AccountTypes accountType,
        UserRole userType
    ) {
        return of(email, password, nickname, accountType, userType);
    }

    public static UsersEntity ofUser(
        UsersEntity user,
        String userName,
        String phone,
        String birthDay,
        GenderTypes gender
    ) {
        return of(user, userName, phone, birthDay, gender);
    }

    private static UsersEntity of(
        String email,
        String password,
        String nickname,
        AccountTypes accountType,
        UserRole userType
    ) {
        log.info("[UserTemplates.of()] email >>> {}, UserType >>> {}", email, userType);
        return UsersEntity.builder()
            .email(email)
            .password(password)
            .userName(null)
            .phone(null)
            .birthDay(null)
            .gender(null)
            .nickname(nickname)
            .status(ENABLED)
            .accountType(accountType)
            .userType(userType)
            .build();
    }
    private static UsersEntity of(
        UsersEntity user,
        String userName,
        String phone,
        String birthDay,
        GenderTypes gender
    ) {
        return UsersEntity.builder()
            .email(user.getEmail())
            .password(user.getPassword())
            .userName(userName)
            .phone(phone)
            .birthDay(birthDay)
            .gender(gender)
            .nickname(user.getNickname())
            .status(user.getStatus())
            .accountType(user.getAccountType())
            .userType(user.getUserType())
            .build();
    }
}
