package edu.dosw.domain.ports.inbound;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.ports.inbound.command.AddUserToConversationCommand;

public interface AddUserToConversationUseCase {
    Conversation execute(AddUserToConversationCommand addUserToConversationCommand);
}
