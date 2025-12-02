package edu.dosw.domain.ports.inbound;

import edu.dosw.domain.ports.inbound.command.DeleteConversationCommand;

public interface DeleteConversationUseCase {
    void execute(DeleteConversationCommand deleteConversationCommand);
}
