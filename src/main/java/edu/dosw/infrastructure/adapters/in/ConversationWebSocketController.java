package edu.dosw.infrastructure.adapters.in;

import edu.dosw.domain.ports.inbound.MarkMessageAsReadUseCase;
import edu.dosw.domain.ports.inbound.SendMessageUseCase;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationMessageRequest;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationMessageResponse;
import edu.dosw.infrastructure.adapters.in.dtos.ReadRequest;
import edu.dosw.infrastructure.adapters.in.dtos.TypingRequest;
import edu.dosw.infrastructure.adapters.in.exceptions.ConversationExceptions;
import edu.dosw.infrastructure.adapters.in.mappers.ConversationMessageWebMapper;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class ConversationWebSocketController {

    private final ConversationMessageWebMapper conversationMessageWebMapper;
    private final SendMessageUseCase sendMessageUseCase;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MarkMessageAsReadUseCase markMessageAsReadUseCase;

    @MessageMapping("/sendMessage")
    public void sendMessage(ConversationMessageRequest message){
        ConversationMessageResponse response = conversationMessageWebMapper.toResponse(sendMessageUseCase.execute(conversationMessageWebMapper.toCommand(message)));
        String destination = "/topic/conversations/" + response.getConversationId();
        simpMessagingTemplate.convertAndSend(destination, response);
    }

    @MessageMapping("/typing")
    public void userTyping(TypingRequest request, Principal principal) {
        if (principal == null) throw ConversationExceptions.failedAuth();
        request.setUserId(principal.getName());
        String destination = "/topic/conversations/" + request.getConversationId() + "/typing";
        simpMessagingTemplate.convertAndSend(destination, request);
    }

    @MessageMapping("/markAsRead")
    public void markAsRead(ReadRequest request, Principal principal) {
        if (principal == null) throw ConversationExceptions.failedAuth();
        request.setUserId(principal.getName());
        markMessageAsReadUseCase.execute(request.getMessageId());
        String destination = "/topic/conversations/" + request.getConversationId() + "/receipts";
        simpMessagingTemplate.convertAndSend(destination, request);
    }
}
