package edu.dosw.infrastructure.adapters.in;

import edu.dosw.domain.ports.inbound.SendMessageUseCase;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationMessageRequest;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationMessageResponse;
import edu.dosw.infrastructure.adapters.in.mappers.ConversationMessageWebMapper;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ConversationWebSocketController {

    private final ConversationMessageWebMapper conversationMessageWebMapper;
    private final SendMessageUseCase sendMessageUseCase;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/sendMessage")
    public void sendMessage(ConversationMessageRequest message){
        ConversationMessageResponse response = conversationMessageWebMapper.toResponse(sendMessageUseCase.execute(conversationMessageWebMapper.toCommand(message)));
        String destination = "/topic/conversations/" + response.getConversationId();
        simpMessagingTemplate.convertAndSend(destination, response);
    }
}
