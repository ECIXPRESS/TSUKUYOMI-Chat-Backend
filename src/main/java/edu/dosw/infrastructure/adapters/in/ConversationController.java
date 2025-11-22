package edu.dosw.infrastructure.adapters.in;

import edu.dosw.application.CreateConversationUseCaseImpl;
import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.DeleteConversationUseCase;
import edu.dosw.domain.ports.inbound.FilterMessagesUseCase;
import edu.dosw.infrastructure.adapters.in.dtos.*;
import edu.dosw.infrastructure.adapters.in.mappers.ConversationMessageWebMapper;
import edu.dosw.infrastructure.adapters.in.mappers.ConversationWebMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eciexpress/conversations")
@AllArgsConstructor
public class ConversationController {

    private final CreateConversationUseCaseImpl createConversation;
    private final ConversationMessageWebMapper conversationMessageWebMapper;
    private final ConversationWebMapper conversationWebMapper;
    private final FilterMessagesUseCase filterMessagesUseCase;
    private final DeleteConversationUseCase deleteConversationUseCase;

    @PostMapping
    public ConversationResponse create(@RequestBody CreateConversationRequest req) {
        return conversationWebMapper.toResponse(createConversation.execute(conversationWebMapper.toCommand(req)));
    }

    @DeleteMapping
    public void delete(@RequestBody DeleteConversationRequest req) {
       deleteConversationUseCase .execute(conversationWebMapper.toCommand(req));
    }

    @GetMapping("/{id}/messages")
    public List<ConversationMessageResponse> getMessages(@PathVariable String id, @RequestParam(required = false) String filterWord)  {
        return filterMessagesUseCase.execute(id,filterWord).stream().map(conversationMessageWebMapper::toResponse).toList();
    }

}
