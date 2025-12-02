package edu.dosw.application;

import edu.dosw.application.applicationmappers.ConversationMessageApplicationMapper;
import edu.dosw.application.exceptions.ApplicationLayerExceptions;
import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.ports.inbound.SendMessageUseCase;
import edu.dosw.domain.ports.inbound.command.SendConversationMessageCommand;
import edu.dosw.domain.ports.outbound.ConversationMessageRepository;
import edu.dosw.domain.ports.outbound.ConversationRepository;
import org.springframework.stereotype.Service;

@Service
public class SendMessageUseCaseImpl implements SendMessageUseCase {

    private final ConversationRepository conversationRepository;
    private final ConversationMessageApplicationMapper conversationMessageApplicationMapper;
    private final ConversationMessageRepository conversationMessageRepository;

    public SendMessageUseCaseImpl(ConversationRepository conversationRepository, ConversationMessageApplicationMapper conversationMessageApplicationMapper, ConversationMessageRepository conversationMessageRepository) {
        this.conversationRepository = conversationRepository;
        this.conversationMessageApplicationMapper = conversationMessageApplicationMapper;
        this.conversationMessageRepository = conversationMessageRepository;
    }

    @Override
    public ConversationMessage execute(SendConversationMessageCommand message) {
        Conversation conversation = conversationRepository.findConversationById(message.getConversationId());
        ConversationMessage domainMessage = conversationMessageApplicationMapper.toDomain(message);

        if(conversation == null) {
            throw ApplicationLayerExceptions.conversationNotFound(message.getConversationId());
        }

        boolean isUserAuthorized = conversation.getUsersIds().stream()
                .anyMatch(userId -> userId.equals(domainMessage.getAuthor()));

        if(!isUserAuthorized) {
            throw ApplicationLayerExceptions.userNotAuthorized();
        }

        conversation.addMessage(domainMessage);
        conversationRepository.updateConversation(conversation);
        conversationMessageRepository.saveConversationMessage(domainMessage);

        return domainMessage;
    }
}
