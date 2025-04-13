package org.baebe.coffeetrading.domains.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.baebe.coffeetrading.domains.common.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "CHAT_GROUPS")
public class ChatGroupsEntity extends BaseTimeEntity {

    private String groupName;

    @OneToMany(mappedBy = "chatGroup")
    private List<ChatGroupUsersEntity> chatGroupUsers = new ArrayList<>();

    @OneToMany(mappedBy = "chatGroup")
    private List<ChatMessagesEntity> chatMessages = new ArrayList<>();

    @Builder
    private ChatGroupsEntity(
        String groupName,
        List<ChatGroupUsersEntity> chatGroupUser,
        List<ChatMessagesEntity> chatMessage
    ) {
        this.groupName = groupName;
        this.chatGroupUsers = chatGroupUser;
        this.chatMessages = chatMessage;
    }
}
