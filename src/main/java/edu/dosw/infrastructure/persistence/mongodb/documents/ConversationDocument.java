package edu.dosw.infrastructure.persistence.mongodb.documents;

import edu.dosw.domain.model.ConversationMessage;
import lombok.Data;

import java.util.List;

@Data
public class ConversationDocument {
    private String id;
    private List<String> usersIds;
    private List<ConversationMessage> messages;

    public ConversationDocument(String id, List<String> usersIds, List<ConversationMessage> messages) {
        this.id = id;
        this.usersIds = usersIds;
        this.messages = messages;
    }

}

