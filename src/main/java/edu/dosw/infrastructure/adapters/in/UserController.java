package edu.dosw.infrastructure.adapters.in;

import edu.dosw.domain.ports.inbound.*;
import edu.dosw.infrastructure.adapters.in.dtos.AddContactRequest;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationMessageResponse;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationResponse;
import edu.dosw.infrastructure.adapters.in.dtos.UserResponse;
import edu.dosw.infrastructure.adapters.in.mappers.ConversationMessageWebMapper;
import edu.dosw.infrastructure.adapters.in.mappers.ConversationWebMapper;
import edu.dosw.infrastructure.adapters.in.mappers.UserWebMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/eciexpress/chatuser")
@AllArgsConstructor
public class UserController {

    private final FilterContactsUseCase filterContactsUseCase;
    private final UserWebMapper userWebMapper;
    private final GetContactsUseCase getContactsUseCase;
    private final GetUserMessagesInConversationUseCase getUserMessagesInConversationUseCase;
    private final ConversationMessageWebMapper conversationMessageWebMapper;
    private final ConversationWebMapper conversationWebMapper;
    private final GetConversationsUseCase getConversationsUseCase;
    private final AddContactUseCase addContactUseCase;

    @GetMapping("/{id}/filter/contacts")
    public List<UserResponse> getContacts(@PathVariable String id, String filterWord) {
        return filterContactsUseCase.execute(id,filterWord)
                .stream()
                .map(userWebMapper::toResponse).toList();
    }

    @GetMapping("/{id}/contacts")
    public List<UserResponse> getContacts(@PathVariable String id) {
        return getContactsUseCase.execute(id)
                .stream()
                .map(userWebMapper::toResponse).toList();
    }

    @GetMapping("/{id}/messages")
    public List<ConversationMessageResponse> getMessages(@PathVariable String id,String conversationId) {
        return getUserMessagesInConversationUseCase.execute(id,conversationId).stream().map(conversationMessageWebMapper::toResponse).toList();
    }

    @GetMapping("/{id}/conversations")
    public List<ConversationResponse> getConversationsOfUser(@PathVariable String id){
        return getConversationsUseCase.execute(id).stream().map(conversationWebMapper::toResponse).toList();
    }

    @PostMapping("/add-contact")
    public void addContact(@RequestBody AddContactRequest request) {
        addContactUseCase.execute(userWebMapper.toCommand(request));
    }
}
