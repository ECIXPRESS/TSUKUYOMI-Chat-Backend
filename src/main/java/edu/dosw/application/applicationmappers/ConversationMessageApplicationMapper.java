package edu.dosw.application.applicationmappers;

import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.model.Regular;
import edu.dosw.domain.ports.inbound.command.SendConversationMessageCommand;
import org.springframework.stereotype.Component;

@Component
public class ConversationMessageApplicationMapper {

    public ConversationMessage toDomain(SendConversationMessageCommand command){
        return new Regular(command.getConversationId(),command.getText(), command.getAuthorId());
    }

}
