package edu.dosw.infrastructure.persistence.mongodb.mappers;


import edu.dosw.domain.model.Conversation;
import edu.dosw.infrastructure.persistence.mongodb.documents.ConversationDocument;
public class ConversationMapper {

    public static ConversationDocument toDocument(Conversation conversation) {
        return new ConversationDocument(conversation.getId(), conversation.getUsersIds(), conversation.getMessages());
    }

    public static Conversation toDomain(ConversationDocument doc) {
        return new Conversation();
    }
}
