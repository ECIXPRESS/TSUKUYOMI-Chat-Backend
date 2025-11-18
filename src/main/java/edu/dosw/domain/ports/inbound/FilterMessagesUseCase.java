package edu.dosw.domain.ports.inbound;

import edu.dosw.domain.model.ConversationMessage;
import java.util.List;

public interface FilterMessagesUseCase {
    List<ConversationMessage> execute(String userId, String filterWord);
}
