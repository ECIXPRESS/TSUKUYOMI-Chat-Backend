package edu.dosw.domain.ports.inbound;

import edu.dosw.domain.model.ConversationMessage;

import java.util.List;

public interface GetMessagesUseCase {
    List<ConversationMessage> execute(String userId);
}
