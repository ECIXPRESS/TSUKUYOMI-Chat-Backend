package edu.dosw.domain.ports.inbound;

import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.ports.inbound.command.SendConversationMessageCommand;

public interface SendMessageUseCase {
    ConversationMessage execute(SendConversationMessageCommand message);
}
