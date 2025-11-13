package edu.dosw.domain.ports;

public interface DeleteConversationUseCase {
    void execute(String conversationId, String userId);
}
