package edu.dosw.infrastructure.adapters.in;

import edu.dosw.application.CreateConversationInteractor;
import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.ports.inbound.GetMessagesUseCase;

import java.util.List;

public class ConversationController {

    private final CreateConversationInteractor createConversationUseCase;
    private final GetMessagesUseCase getMessagesUseCase;

    public ConversationController(CreateConversationInteractor createConversationUseCase, GetMessagesUseCase getMessagesUseCase) {
        this.createConversationUseCase = createConversationUseCase;
        this.getMessagesUseCase = getMessagesUseCase;
    }

    @PostMapping
    public Conversation create(@RequestBody CreateConversationRequest req) {
        return createConversationUseCase.execute(req);
    }

    @GetMapping("/{id}/messages")
    public List<ConversationMessage> getMessages(@PathVariable String id) {
        return getMessagesUseCase.execute(id);
    }
}
