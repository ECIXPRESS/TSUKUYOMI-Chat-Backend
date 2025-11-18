package edu.dosw.infrastructure.adapters.in;

import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.FilterContactsUseCase;
import edu.dosw.domain.ports.inbound.GetContactsUseCase;
import edu.dosw.domain.ports.inbound.GetUserMessagesInConversationUseCase;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationMessageResponse;
import edu.dosw.infrastructure.adapters.in.dtos.UserResponse;
import edu.dosw.infrastructure.adapters.in.mappers.ConversationMessageWebMapper;
import edu.dosw.infrastructure.adapters.in.mappers.UserWebMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/{id}/contacts")
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

}
