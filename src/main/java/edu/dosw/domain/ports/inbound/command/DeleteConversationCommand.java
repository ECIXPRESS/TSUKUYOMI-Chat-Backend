package edu.dosw.domain.ports.inbound.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteConversationCommand {
    private String userId;
    private String conversationId;
}
