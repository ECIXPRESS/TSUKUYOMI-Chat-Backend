package edu.dosw.domain.ports.inbound;

import edu.dosw.domain.model.Conversation;

import java.util.List;

public interface GetConversationsUseCase {
    List<Conversation> execute(String id);
}
