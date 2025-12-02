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
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
@AllArgsConstructor
@Slf4j
public class ConversationWebSocketController {

    private final ConversationMessageWebMapper conversationMessageWebMapper;
    private final SendMessageUseCase sendMessageUseCase;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MarkMessageAsReadUseCase markMessageAsReadUseCase;

    @MessageMapping("/sendMessage")
    public void sendMessage(ConversationMessageRequest message, @Header("userId") String userId) {
        if (userId == null) {
            log.error("Usuario no autenticado intentando enviar mensaje");
            throw ConversationExceptions.failedAuth();
        }
        message.setAuthorId(userId);
        ConversationMessageResponse response = conversationMessageWebMapper.toResponse(sendMessageUseCase.execute(conversationMessageWebMapper.toCommand(message)));
        String destination = "/topic/conversations/" + response.getConversationId();
        simpMessagingTemplate.convertAndSend(destination, response);
    }

    @MessageMapping("/typing")
    public void userTyping(TypingRequest request, @Header("userId") String userId) {
        if (userId == null) {
            log.error("Usuario no autenticado");
            throw ConversationExceptions.failedAuth();
        }
        request.setUserId(userId);
        String destination = "/topic/conversations/" + request.getConversationId() + "/typing";
        simpMessagingTemplate.convertAndSend(destination, request);
    }

    @MessageMapping("/markAsRead")
    public void markAsRead(ReadRequest request, @Header("userId") String userId) {
        if (userId == null) {
            log.error("Usuario no autenticado");
            throw ConversationExceptions.failedAuth();
        }
        request.setUserId(userId);
        markMessageAsReadUseCase.execute(request.getMessageId());
        String destination = "/topic/conversations/" + request.getConversationId() + "/receipts";
        simpMessagingTemplate.convertAndSend(destination, request);
        log.info("✅ Mensaje {} marcado como leído por {}", request.getMessageId(), userId);

    }
}
