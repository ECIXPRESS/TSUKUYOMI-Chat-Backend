package edu.dosw.domain.ports.inbound;

import edu.dosw.domain.model.ConversationMessage;
import org.apache.logging.log4j.message.Message;

public interface SendMessageUseCase {
    void execute(ConversationMessage message);
}
