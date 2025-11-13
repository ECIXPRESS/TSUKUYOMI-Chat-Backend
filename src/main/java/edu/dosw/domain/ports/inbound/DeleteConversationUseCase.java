package edu.dosw.domain.ports.inbound;

public interface DeleteConversationUseCase {
    void execute(String conversationId, String userId);
}
