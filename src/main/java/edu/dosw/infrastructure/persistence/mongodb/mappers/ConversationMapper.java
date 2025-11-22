package edu.dosw.infrastructure.persistence.mongodb.mappers;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.model.User;
import edu.dosw.infrastructure.persistence.mongodb.documents.ConversationDocument;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ConversationMapper {

    public Conversation toDomain(
            ConversationDocument doc,
            Function<String, ConversationMessage> findMessage
    ) {
        if (doc == null) return null;

        List<ConversationMessage> messages = doc.getMessages() == null
                ? new ArrayList<>()
                : doc.getMessages().stream()
                .map(findMessage)
                .collect(Collectors.toCollection(ArrayList::new));

        return new Conversation(doc.getId(),doc.getCreationDate(),doc.getUsers(),messages);

    }

    public ConversationDocument toDocument(Conversation conversation) {
        if (conversation == null) return null;

        return new ConversationDocument(
                conversation.getId(),
                conversation.getCreationDate(),
                conversation.getUsersIds(),
                conversation.getMessages().stream().map(ConversationMessage::getId).collect(Collectors.toCollection(ArrayList::new)),
                new java.util.Date()
        );
    }
}
