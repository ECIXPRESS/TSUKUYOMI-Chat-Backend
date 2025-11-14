package edu.dosw.infrastructure.persistence.mongodb.repositories;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.ports.outbound.ConversationRepository;

import java.util.List;

public class MongoConversationRepository implements ConversationRepository {
    @Override
    public void saveConversation(Conversation conversation) {

    }

    @Override
    public Conversation searchConversationByUserId(String userId) {
        return null;
    }

    @Override
    public List<Conversation> listConversations() {
        return List.of();
    }

    @Override
    public void deleteConversation(String conversationId) {

    }

    @Override
    public Conversation findConversationById(String conversationId) {
        return null;
    }
}
