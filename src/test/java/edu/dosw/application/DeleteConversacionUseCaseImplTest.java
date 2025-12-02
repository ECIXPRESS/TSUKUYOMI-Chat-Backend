package edu.dosw.application;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.command.DeleteConversationCommand;
import edu.dosw.domain.ports.outbound.ConversationRepository;
import edu.dosw.domain.ports.outbound.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteConversacionUseCaseImplTest {

    @Mock
    private ConversationRepository conversationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DeleteConversacionUseCaseImpl deleteConversacionUseCase;

    private Conversation conversation;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setUp() {
        List<String> userIds = new ArrayList<>(Arrays.asList("user-1", "user-2", "user-3"));
        conversation = new Conversation(userIds, "order-123");

        user1 = new User("user-1", "Juan", null);
        user1.addConversation(conversation.getId());

        user2 = new User("user-2", "Pedro", null);
        user2.addConversation(conversation.getId());

        user3 = new User("user-3", "Maria", null);
        user3.addConversation(conversation.getId());
    }

    @Test
    void execute_shouldRemoveUserAndUpdateConversation_whenMoreThanTwoUsersRemain() {
        // Arrange
        DeleteConversationCommand command = new DeleteConversationCommand("user-1", conversation.getId());

        when(conversationRepository.findConversationById(conversation.getId())).thenReturn(conversation);
        when(userRepository.findUserById("user-1")).thenReturn(user1);

        // Act
        deleteConversacionUseCase.execute(command);

        // Assert
        verify(conversationRepository).updateConversation(conversation);
        verify(conversationRepository, never()).deleteConversation(any());
        verify(userRepository).updateUser(user1);
        verify(userRepository, times(1)).updateUser(any(User.class));
    }

    @Test
    void execute_shouldDeleteConversation_whenOnlyOneUserWouldRemain() {
        // Arrange
        List<String> twoUsers = new ArrayList<>(Arrays.asList("user-1", "user-2"));
        Conversation smallConversation = new Conversation(twoUsers, "order-456");
        User remainingUser = new User("user-2", "Pedro", null);

        DeleteConversationCommand deleteCommand = new DeleteConversationCommand(
                "user-1",
                smallConversation.getId()
        );

        when(conversationRepository.findConversationById(smallConversation.getId())).thenReturn(smallConversation);
        when(userRepository.findUserById("user-1")).thenReturn(user1);
        when(userRepository.findUserById("user-2")).thenReturn(remainingUser);

        // Act
        deleteConversacionUseCase.execute(deleteCommand);

        // Assert
        verify(conversationRepository).deleteConversation(smallConversation.getId());
        verify(conversationRepository, never()).updateConversation(any());
        verify(userRepository, times(2)).updateUser(any(User.class));
    }

    @Test
    void execute_shouldThrowException_whenConversationNotFound() {
        // Arrange
        DeleteConversationCommand command = new DeleteConversationCommand("user-1", conversation.getId());
        when(conversationRepository.findConversationById(conversation.getId())).thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> deleteConversacionUseCase.execute(command))
                .isInstanceOf(RuntimeException.class);

        verify(userRepository, never()).updateUser(any());
        verify(conversationRepository, never()).updateConversation(any());
        verify(conversationRepository, never()).deleteConversation(any());
    }

    @Test
    void execute_shouldThrowException_whenUserNotFound() {
        // Arrange
        DeleteConversationCommand command = new DeleteConversationCommand("user-1", conversation.getId());
        when(conversationRepository.findConversationById(conversation.getId())).thenReturn(conversation);
        when(userRepository.findUserById("user-1")).thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> deleteConversacionUseCase.execute(command))
                .isInstanceOf(RuntimeException.class);

        verify(userRepository, never()).updateUser(any());
        verify(conversationRepository, never()).updateConversation(any());
        verify(conversationRepository, never()).deleteConversation(any());
    }

    @Test
    void execute_shouldRemoveConversationFromAllRemainingUsers_whenDeletingConversation() {
        // Arrange
        List<String> twoUsers = new ArrayList<>(Arrays.asList("user-1", "user-2"));
        Conversation smallConversation = new Conversation(twoUsers, "order-789");

        DeleteConversationCommand deleteCommand = new DeleteConversationCommand(
                "user-1",
                smallConversation.getId()
        );

        when(conversationRepository.findConversationById(smallConversation.getId())).thenReturn(smallConversation);
        when(userRepository.findUserById("user-1")).thenReturn(user1);
        when(userRepository.findUserById("user-2")).thenReturn(user2);

        // Act
        deleteConversacionUseCase.execute(deleteCommand);

        // Assert
        verify(userRepository).updateUser(argThat(user ->
                user.getId().equals("user-1")
        ));
        verify(userRepository).updateUser(argThat(user ->
                user.getId().equals("user-2")
        ));
        verify(conversationRepository).deleteConversation(smallConversation.getId());
    }
}
