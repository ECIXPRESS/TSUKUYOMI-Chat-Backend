package edu.dosw.infrastructure.adapters.in.mappers;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.ports.inbound.command.CreateConversationCommand;
import edu.dosw.domain.ports.inbound.command.DeleteConversationCommand;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationMessageResponse;
import edu.dosw.infrastructure.adapters.in.dtos.CreateConversationRequest;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationResponse;
import edu.dosw.infrastructure.adapters.in.dtos.DeleteConversationRequest;
import org.springframework.stereotype.Component;

@Component
public class ConversationWebMapper {

    public ConversationResponse toResponse(Conversation conversation){
        return new ConversationResponse(
                conversation.getId(),
                conversation.getCreationDate(),
                conversation.getUsersIds(),
                conversation.getMessages()
                        .stream()
                        .map(m -> new ConversationMessageResponse(
                                m.getId(),
                                m.getConversationId(),
                                m.getAuthor(),
                                m.getText(),
                                m.getSendDate(),
                                m.getIsRead()
                        )).toList(),
                conversation.getOrderId()
        );
    }

    public CreateConversationCommand toCommand(CreateConversationRequest request){
        return new CreateConversationCommand(
                request.getUsersIds(),
                request.getOrderId()
        );
    }

    public DeleteConversationCommand toCommand(DeleteConversationRequest request){
        return new DeleteConversationCommand(
                request.getUserId(),
                request.getConversationId()
        );
    }
}
