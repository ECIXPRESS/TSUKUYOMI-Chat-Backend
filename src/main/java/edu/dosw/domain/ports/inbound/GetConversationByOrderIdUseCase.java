package edu.dosw.domain.ports.inbound;

import edu.dosw.domain.model.Conversation;

public interface GetConversationByOrderIdUseCase {
    Conversation execute(String orderId);
}
