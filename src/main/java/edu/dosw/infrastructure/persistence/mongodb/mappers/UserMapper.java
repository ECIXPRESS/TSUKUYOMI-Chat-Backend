package edu.dosw.infrastructure.persistence.mongodb.mappers;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.model.User;
import edu.dosw.infrastructure.persistence.mongodb.documents.UserDocument;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

@Component
public class UserMapper {

    public User toDomain(
            UserDocument doc,
            Function<String, Conversation> findConversation
    ) {
        if (doc == null) return null;

        List<Conversation> conversations = doc.getConversations() == null
                ? List.of()
                : doc.getConversations().stream()
                .map(findConversation)
                .toList();

        return new User(
                doc.getId(),
                doc.getName(),
                doc.getProfilePhoto(),
                doc.isActive(),
                conversations,
                doc.getContacts()
        );
    }

    public UserDocument toDocument(User user) {
        if (user == null) return null;

        return new UserDocument(
                user.getId(),
                user.getName(),
                user.getProfilePhoto(),
                user.getIsActive(),
                user.getConversations() == null
                        ? List.of()
                        : user.getConversations().stream()
                        .map(Conversation::getId)
                        .toList(),
                user.getContacts()
        );
    }
}
