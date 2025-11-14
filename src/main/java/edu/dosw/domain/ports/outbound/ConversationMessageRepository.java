package edu.dosw.domain.ports.outbound;

import edu.dosw.domain.model.ConversationMessage;
import org.apache.logging.log4j.message.Message;

import java.util.List;

public interface ConversationMessageRepository {
    void saveConversationMessage(ConversationMessage conversationMessage);
    ConversationMessage findMessageById(String messageId);
    List<ConversationMessage> findMessagesByConversationId(String conversationId);
    void updateConversationMessage(ConversationMessage conversationMessage);
    void deleteMessageById(String messageId);
}
