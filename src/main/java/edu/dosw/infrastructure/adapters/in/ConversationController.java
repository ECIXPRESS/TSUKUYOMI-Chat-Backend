package edu.dosw.infrastructure.adapters.in;

import edu.dosw.application.CreateConversationUseCaseImpl;
import edu.dosw.application.SendMessageUseCaseImpl;
import edu.dosw.domain.ports.inbound.FilterMessagesUseCase;
import edu.dosw.domain.ports.inbound.GetConversationsUseCase;
import edu.dosw.domain.ports.inbound.GetUserMessagesInConversationUseCase;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationMessageRequest;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationMessageResponse;
import edu.dosw.infrastructure.adapters.in.dtos.CreateConversationRequest;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationResponse;
import edu.dosw.infrastructure.adapters.in.mappers.ConversationMessageWebMapper;
import edu.dosw.infrastructure.adapters.in.mappers.ConversationWebMapper;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eciexpress/conversations")
@AllArgsConstructor
public class ConversationController {

    private final CreateConversationUseCaseImpl createConversation;
    private final GetUserMessagesInConversationUseCase getUserMessagesUseCase;
    private final ConversationMessageWebMapper conversationMessageWebMapper;
    private final SendMessageUseCaseImpl sendMessageHandler;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ConversationWebMapper conversationWebMapper;
    private final GetConversationsUseCase conversationsUseCase;
    private final FilterMessagesUseCase filterMessagesUseCase;

    @PostMapping
    public ConversationResponse create(@RequestBody CreateConversationRequest req) {
        return conversationWebMapper.toResponse(createConversation.execute(conversationWebMapper.toCommand(req)));
    }

    @GetMapping("/{id}/conversations")
    public List<ConversationResponse> getConversationsOfUser(@PathVariable String id){
        return conversationsUseCase.execute(id).stream().map(conversationWebMapper::toResponse).toList();
    }

    @GetMapping("/{id}/messages")
    public List<ConversationMessageResponse> getMessages(@PathVariable String id,String filterWord) {
        return filterMessagesUseCase.execute(id,filterWord).stream().map(conversationMessageWebMapper::toResponse).toList();
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(ConversationMessageRequest message){
        ConversationMessageResponse response = conversationMessageWebMapper.toResponse(sendMessageHandler.execute(conversationMessageWebMapper.toCommand(message)));
        String destination = "/topic/conversations/" + response.getConversationId();
        simpMessagingTemplate.convertAndSend(destination, response);
    }

}
