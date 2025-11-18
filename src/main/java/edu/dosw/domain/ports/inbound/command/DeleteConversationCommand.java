package edu.dosw.domain.ports.inbound.command;

import lombok.Data;

@Data
public class DeleteConversationCommand {
    private String userId;
    private String conversationId;
}
