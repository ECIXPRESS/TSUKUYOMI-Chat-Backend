package edu.dosw.infrastructure.persistence.mongodb.mappers;

import edu.dosw.domain.model.User;
import edu.dosw.infrastructure.persistence.mongodb.documents.UserDocument;
import org.springframework.stereotype.Component;


@Component
public class UserMapper {

    public User toDomain(UserDocument doc) {
        return new User(
                doc.getId(),
                doc.getName(),
                doc.getProfilePhoto(),
                doc.getConversations(),
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
                user.getConversations(),
                user.getContacts()
        );
    }
}
