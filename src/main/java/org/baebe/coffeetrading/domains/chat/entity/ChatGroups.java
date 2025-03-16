package org.baebe.coffeetrading.domains.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "CHAT_GROUPS")
public class ChatGroups extends ChatGroupsEntity{

    private ChatGroups(
        String groupName,
        List<ChatGroupUsers> chatGroupUser,
        List<ChatMessages> chatMessage
    ) {
        this.setGroupName(groupName);
        this.setChatGroupUsers(chatGroupUser);
        this.setChatMessages(chatMessage);
    }
}
