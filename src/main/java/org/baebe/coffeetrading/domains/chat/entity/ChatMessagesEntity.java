package org.baebe.coffeetrading.domains.chat.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@AttributeOverride(name = "createdAt", column = @Column(name = "SENDED_AT"))
@Table(name = "CHAT_MESSAGES")
public class ChatMessagesEntity extends BaseTimeEntity {

    @Column(name = "CONTENT", length = 4000)
    private String messageContent;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private UsersEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHATGROUP_ID")
    private ChatGroupsEntity chatGroup;

    @Builder
    private ChatMessagesEntity(
        String messageContent,
        UsersEntity user,
        ChatGroupsEntity chatGroup
    ) {
        this.messageContent = messageContent;
        this.user = user;
        this.chatGroup = chatGroup;
    }
}
