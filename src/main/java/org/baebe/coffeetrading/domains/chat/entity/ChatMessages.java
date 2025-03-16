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
@AttributeOverride(name = "createdAt", column = @Column(name = "SENDED_AT"))
@Table(name = "CHAT_MESSAGES")
public class ChatMessages extends ChatMessagesEntity{

    private ChatMessages(
        String messageContent,
        Users user,
        ChatGroups chatGroup
    ) {
        this.setMessageContent(messageContent);
        this.setUser(user);
        this.setChatGroup(chatGroup);
    }
}
