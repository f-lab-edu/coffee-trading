package org.baebe.coffeetrading.domains.chat.entity;

import jakarta.persistence.AttributeOverride;
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
import org.baebe.coffeetrading.domains.common.BaseTimeEntity;
import org.baebe.coffeetrading.domains.user.entity.UsersEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AttributeOverride(name = "createdAt", column = @Column(name = "ACCESSED_AT"))
@Table(name = "CHAT_GROUP_USERS")
public class ChatGroupUsersEntity extends BaseTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UsersEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHATGROUP_ID")
    private ChatGroupsEntity chatGroup;

    @Builder
    private ChatGroupUsersEntity(
        UsersEntity user,
        ChatGroupsEntity chatGroup
    ) {
        this.user = user;
        this.chatGroup = chatGroup;
    }
}
