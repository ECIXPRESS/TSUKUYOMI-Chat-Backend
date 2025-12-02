package edu.dosw.application;

import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.ports.inbound.GetUserMessagesInConversationUseCase;
import edu.dosw.domain.ports.outbound.ConversationMessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUserMessagesUseCaseImpl implements GetUserMessagesInConversationUseCase {

    private final ConversationMessageRepository conversationMessageRepository;

    public GetUserMessagesUseCaseImpl(ConversationMessageRepository conversationMessageRepository) {
        this.conversationMessageRepository = conversationMessageRepository;
    }

    @Override
    public List<ConversationMessage> execute(String userId,String conversationId) {
        return conversationMessageRepository.findMessagesByUserId(userId).stream().filter(c -> c.getConversationId().equals(conversationId) ).toList();
    }
}
