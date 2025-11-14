package edu.dosw.infrastructure.persistence.mongodb.mappers;

import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.infrastructure.persistence.mongodb.documents.ConversationMessageDocument;
import org.springframework.stereotype.Component;

@Component
public class ConversationMessageMapper {

    public ConversationMessageDocument toDocument(ConversationMessage message) {
        if (message == null) return null;

        ConversationMessageDocument doc = new ConversationMessageDocument();
        doc.setId(message.getId());
        doc.setConversationId(message.getConversationId());
        doc.setAuthorId(message.getAuthor());
        doc.setText(message.getText());
        doc.setSendDate(message.getSendDate());
        doc.setRead(message.getIsRead());
        return doc;
    }

    public ConversationMessage toDomain(ConversationMessageDocument doc) {
        if (doc == null) return null;

        return new ConversationMessage(
                doc.getId(),
                doc.getConversationId(),
                doc.getSendDate(),
                doc.getText(),
                doc.getAuthorId()
        ) {
        };
    }
}
