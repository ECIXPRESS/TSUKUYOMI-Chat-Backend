package edu.dosw.application;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.ports.inbound.GetConversationByOrderIdUseCase;
import edu.dosw.domain.ports.outbound.ConversationRepository;
import org.springframework.stereotype.Service;

@Service
public class GetConversationByOrderIdUseCaseImpl implements GetConversationByOrderIdUseCase {

    private final ConversationRepository conversationRepository;

    public GetConversationByOrderIdUseCaseImpl(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Override
    public Conversation execute(String orderId) {
        return conversationRepository.findConversationByOrderId(orderId);
    }
}
