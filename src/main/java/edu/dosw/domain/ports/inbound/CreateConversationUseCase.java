package edu.dosw.domain.ports.inbound;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.ports.inbound.command.CreateConversationCommand;

public interface CreateConversationUseCase {
    Conversation execute(CreateConversationCommand createConversationRequest);
}
