package edu.dosw.infrastructure.adapters.in;

import edu.dosw.domain.model.User;
import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.ports.inbound.*;
import edu.dosw.infrastructure.adapters.in.dtos.*;
import edu.dosw.infrastructure.adapters.in.mappers.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private FilterContactsUseCase filterContactsUseCase;

    @Mock
    private UserWebMapper userWebMapper;

    @Mock
    private GetContactsUseCase getContactsUseCase;

    @Mock
    private GetUserMessagesInConversationUseCase getUserMessagesInConversationUseCase;

    @Mock
    private ConversationMessageWebMapper conversationMessageWebMapper;

    @Mock
    private ConversationWebMapper conversationWebMapper;

    @Mock
    private GetConversationsUseCase getConversationsUseCase;

    @Mock
    private AddContactUseCase addContactUseCase;

    @InjectMocks
    private UserController userController;



    @Test
    void getConversationsOfUser_ReturnsConversations() {
        String userId = "user1";
        Conversation conversation = new Conversation("conv1", Instant.now(), Arrays.asList("user1", "user2"), null, null);
        ConversationResponse response = new ConversationResponse("conv1", Instant.now(), Arrays.asList("user1", "user2"), null, null);

        when(getConversationsUseCase.execute(userId)).thenReturn(Arrays.asList(conversation));
        when(conversationWebMapper.toResponse(conversation)).thenReturn(response);

        List<ConversationResponse> result = userController.getConversationsOfUser(userId);

        assertEquals(1, result.size());
        verify(getConversationsUseCase).execute(userId);
    }

    @Test
    void addContact_Success() {
        AddContactRequest request = new AddContactRequest();
        request.setUserId("user1");
        request.setContactId("contact1");

        doNothing().when(addContactUseCase).execute(any());

        userController.addContact(request);

        verify(addContactUseCase).execute(any());
        verify(userWebMapper).toCommand(request);
    }
}
