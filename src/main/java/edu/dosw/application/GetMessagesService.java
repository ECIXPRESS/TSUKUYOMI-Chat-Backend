package edu.dosw.application;

import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.ports.inbound.GetMessagesUseCase;
import edu.dosw.domain.ports.outbound.ConversationMessageRepository;

import java.util.List;

public class GetMessagesService implements GetMessagesUseCase {

    private final ConversationMessageRepository conversationMessageRepository;

    public GetMessagesService(ConversationMessageRepository conversationMessageRepository) {
        this.conversationMessageRepository = conversationMessageRepository;
    }

    @Override
    public List<ConversationMessage> execute(String userId) {
        return conversationMessageRepository.findMessagesByUserId(userId);
    }
}
