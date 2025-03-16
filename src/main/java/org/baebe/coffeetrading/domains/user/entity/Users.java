package org.baebe.coffeetrading.domains.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.commons.types.user.AccountTypes;
import org.baebe.coffeetrading.commons.types.user.GenderTypes;
import org.baebe.coffeetrading.commons.types.user.UserRole;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "USERS")
public class Users extends UsersEntity{

    @Builder
    private Users(
        String email,
        String password,
        String userName,
        String phone,
        String birthday,
        GenderTypes gender,
        String nickname,
        AccountTypes accountType,
        UserRole userType
    ) {
        this.setEmail(email);
        this.setPassword(password);
        this.setUserName(userName);
        this.setPhone(phone);
        this.setBirthday(birthday);
        this.setGender(gender);
        this.setNickname(nickname);
        this.setAccountType(accountType);
        this.setUserType(userType);
    }
}
