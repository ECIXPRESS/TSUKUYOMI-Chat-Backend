package edu.dosw.application;

import edu.dosw.application.applicationmappers.ConversationMessageApplicationMapper;
import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.ports.inbound.SendMessageUseCase;
import edu.dosw.domain.ports.inbound.command.SendConversationMessageCommand;
import edu.dosw.domain.ports.outbound.ConversationRepository;

public class SendMessageUseCaseImpl implements SendMessageUseCase {

    private final ConversationRepository conversationRepository;
    private final ConversationMessageApplicationMapper conversationMessageApplicationMapper;

    public SendMessageUseCaseImpl(ConversationRepository conversationRepository, ConversationMessageApplicationMapper conversationMessageApplicationMapper) {
        this.conversationRepository = conversationRepository;
        this.conversationMessageApplicationMapper = conversationMessageApplicationMapper;
    }

    @Override
    public ConversationMessage execute(SendConversationMessageCommand message) {
        Conversation conversation = conversationRepository.findConversationById(message.getConversationId());
        ConversationMessage domainMessage = conversationMessageApplicationMapper.toDomain(message);
        if(conversation != null && conversation.getUsersIds().stream().filter(u -> u.equals(domainMessage.getAuthor())).findFirst().orElse(null) != null){
            conversation.addMessage(domainMessage);
            conversationRepository.updateConversation(conversation);
        }else{
            //exception
        }
        return domainMessage;
    }
}
