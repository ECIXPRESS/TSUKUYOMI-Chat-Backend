package edu.dosw.infrastructure.adapters.in.mappers;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.ports.inbound.command.CreateConversationCommand;
import edu.dosw.domain.ports.inbound.command.DeleteConversationCommand;
import edu.dosw.infrastructure.adapters.in.dtos.CreateConversationRequest;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationResponse;
import edu.dosw.infrastructure.adapters.in.dtos.DeleteConversationRequest;

public class ConversationWebMapper {
    public ConversationResponse toResponse(Conversation conversation){

    }

    public CreateConversationCommand toCommand(CreateConversationRequest createConversationRequest){

    }

    public DeleteConversationCommand toCommand(DeleteConversationRequest deleteConversationRequest){

    }
}
