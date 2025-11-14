package edu.dosw.domain.ports.outbound;

import edu.dosw.domain.model.Conversation;

import java.util.List;

public interface ConversationRepository {
    void saveConversation(Conversation conversation);
    Conversation searchConversationByUserId(String userId);
    List<Conversation> listConversations();
    void deleteConversation(String conversationId);
    Conversation findConversationById(String conversationId);
    void updateConversation(Conversation conversation);
}
