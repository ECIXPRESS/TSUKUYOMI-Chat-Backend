package edu.dosw.domain.ports.inbound.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendConversationMessageCommand {
    private String conversationId;
    private String authorId;
    private String text;
}
