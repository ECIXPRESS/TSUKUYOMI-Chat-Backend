package edu.dosw.application;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.ports.inbound.AddUserToConversationUseCase;
import edu.dosw.domain.ports.inbound.command.AddUserToConversationCommand;

public class AddUserToConversationUseCaseImpl implements AddUserToConversationUseCase {

    @Override
    public Conversation execute(AddUserToConversationCommand addUserToConversationCommand) {
        return null;
    }
}
