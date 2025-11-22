package edu.dosw.infrastructure.adapters.in;

import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.*;
import edu.dosw.domain.ports.outbound.UserRepository;
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
    private UserRepository userRepository; //borar despues de la prueba junto con /create-testusers

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
    public List<ConversationMessageResponse> getMessagesInConversation(@PathVariable String id, String conversationId) {
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

    @PostMapping("/create-test-users")
    public String createTestUsers() {
        // Crear usuario 1
        User user1 = new User("Nikolas","Nikolas",null);
        userRepository.saveUser(user1);

        // Crear usuario 2
        User user2 = new User("Manuel","Manuel",null);
        userRepository.saveUser(user2);

        // Crear usuario 2
        User user3 = new User("Martin","Martin",null);
        userRepository.saveUser(user3);

        return "✅ Usuarios de prueba creados: user1, user2";
    }
}
