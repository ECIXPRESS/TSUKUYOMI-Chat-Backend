package edu.dosw.infrastructure.adapters.in.mappers;

import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.ports.inbound.command.SendConversationMessageCommand;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationMessageRequest;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationMessageResponse;
import org.springframework.stereotype.Component;

@Component
public class ConversationMessageWebMapper {
    public SendConversationMessageCommand toCommand(ConversationMessageRequest conversationMessageRequest){
        return new SendConversationMessageCommand(conversationMessageRequest.getConversationId(), conversationMessageRequest.getAuthorId(), conversationMessageRequest.getText());
    }
    public ConversationMessageResponse toResponse(ConversationMessage message){
        return new ConversationMessageResponse(
                message.getId(),
                message.getConversationId(),
                message.getAuthor(),
                message.getText(),
                message.getSendDate(),
                message.getIsRead()
        );
    }
}
