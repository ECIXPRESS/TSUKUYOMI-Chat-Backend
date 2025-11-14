package edu.dosw.infrastructure.persistence.mongodb.mappers;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.model.User;
import edu.dosw.infrastructure.persistence.mongodb.documents.ConversationDocument;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class ConversationMapper {

    public Conversation toDomain(
            ConversationDocument doc,
            Function<String, User> findUser,
            Function<String, ConversationMessage> findMessage
    ) {
        if (doc == null) return null;

        List<User> users = doc.getParticipants() == null
                ? List.of()
                : doc.getParticipants().stream()
                .map(findUser)
                .toList();

        List<ConversationMessage> messages = doc.getMessages() == null
                ? List.of()
                : doc.getMessages().stream()
                .map(findMessage)
                .toList();

        Conversation conversation = new Conversation(
                doc.getId(),
                doc.getCreationDate(),
                doc.getParticipants()
        );

        // populate lists
        users.forEach(conversation::addUser);
        messages.forEach(conversation::addMessage);

        return conversation;
    }

    public ConversationDocument toDocument(Conversation conversation) {
        if (conversation == null) return null;

        return new ConversationDocument(
                conversation.getId(),
                conversation.getCreationDate(),
                conversation.getUsersIds(),
                conversation.getMessages().stream().map(ConversationMessage::getId).toList(),
                new java.util.Date()
        );
    }
}
