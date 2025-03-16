package org.baebe.coffeetrading.domains.chat.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.domains.user.entity.Users;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AttributeOverride(name = "createdAt", column = @Column(name = "ACCESSED_AT"))
@Table(name = "CHAT_GROUP_USERS")
public class ChatGroupUsers extends ChatGroupUsersEntity{

    private ChatGroupUsers(
        Users user,
        ChatGroups chatGroup
    ) {
        this.setUser(user);
        this.setChatGroup(chatGroup);
    }
}
