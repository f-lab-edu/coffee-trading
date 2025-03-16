package org.baebe.coffeetrading.domains.chat.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.baebe.coffeetrading.domains.common.BaseCreatedAtEntity;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class ChatGroupsEntity extends BaseCreatedAtEntity {

    private String groupName;

    @OneToMany(mappedBy = "chatGroup")
    private List<ChatGroupUsers> chatGroupUsers = new ArrayList<>();

    @OneToMany(mappedBy = "chatGroup")
    private List<ChatMessages> chatMessages = new ArrayList<>();
}
