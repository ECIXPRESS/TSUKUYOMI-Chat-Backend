package edu.dosw.application;

import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.ports.inbound.MarkMessageAsReadUseCase;
import edu.dosw.domain.ports.outbound.ConversationRepository;
import org.apache.logging.log4j.message.Message;

public class MarkMessageAsReadService implements MarkMessageAsReadUseCase {

    private final ConversationRepository conversationRepository;

    public MarkMessageAsReadService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Override
    public void execute(ConversationMessage message) {
        message.markRead();
    }
}
