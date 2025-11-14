package edu.dosw.domain.ports.inbound;

import edu.dosw.domain.model.ConversationMessage;

public interface MarkMessageAsReadUseCase {
    void execute(String messageId);
}
