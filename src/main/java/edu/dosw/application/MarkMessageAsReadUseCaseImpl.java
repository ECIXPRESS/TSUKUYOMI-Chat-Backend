package edu.dosw.application;

import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.ports.inbound.MarkMessageAsReadUseCase;
import edu.dosw.domain.ports.outbound.ConversationMessageRepository;

public class MarkMessageAsReadUseCaseImpl implements MarkMessageAsReadUseCase {

    private final ConversationMessageRepository conversationMessageRepository;

    public MarkMessageAsReadUseCaseImpl(ConversationMessageRepository conversationMessageRepository) {
        this.conversationMessageRepository = conversationMessageRepository;
    }

    @Override
    public void execute(String messageId) {
        ConversationMessage refMessage = conversationMessageRepository.findMessageById(messageId);
        refMessage.markRead();
        conversationMessageRepository.updateConversationMessage(refMessage);
    }
}
