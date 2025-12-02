package edu.dosw.application;

import edu.dosw.application.exceptions.ApplicationLayerExceptions;
import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.command.CreateConversationCommand;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateConversationUseCaseImplTest {

    @Mock
    private ConversationRepository conversationRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CreateConversationUseCaseImpl createConversationUseCase;

    private User user1;
    private User user2;
    private CreateConversationCommand command;

    @BeforeEach
    void setUp() {
        user1 = new User("user-1", "Juan", null);
        user2 = new User("user-2", "Pedro", null);

        List<String> userIds = Arrays.asList("user-1", "user-2");
        command = new CreateConversationCommand(userIds, "order-123");
    }

    @Test
    void execute_shouldCreateConversation_whenAtLeastTwoValidUsersExist() {
        // Arrange
        when(userRepository.findUserById("user-1")).thenReturn(user1);
        when(userRepository.findUserById("user-2")).thenReturn(user2);

        // Act
        Conversation result = createConversationUseCase.execute(command);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUsersIds()).hasSize(2);
        assertThat(result.getOrderId()).isEqualTo("order-123");

        verify(conversationRepository).saveConversation(any(Conversation.class));
        verify(userRepository, times(2)).updateUser(any(User.class));
    }

    @Test
    void execute_shouldThrowException_whenLessThanTwoValidUsers() {
        // Arrange
        when(userRepository.findUserById("user-1")).thenReturn(user1);
        when(userRepository.findUserById("user-2")).thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> createConversationUseCase.execute(command))
                .isInstanceOf(RuntimeException.class);

        verify(conversationRepository, never()).saveConversation(any());
        verify(userRepository, never()).updateUser(any());
    }

    @Test
    void execute_shouldFilterOutInvalidUsers_andCreateConversationWithValidOnes() {
        // Arrange
        User user3 = new User("user-3", "Maria", null);
        List<String> userIds = Arrays.asList("user-1", "user-2", "user-999");
        CreateConversationCommand commandWithInvalidUser = new CreateConversationCommand(userIds, "order-456");

        when(userRepository.findUserById("user-1")).thenReturn(user1);
        when(userRepository.findUserById("user-2")).thenReturn(user2);
        when(userRepository.findUserById("user-999")).thenReturn(null);

        // Act
        Conversation result = createConversationUseCase.execute(commandWithInvalidUser);

        // Assert
        assertThat(result.getUsersIds()).hasSize(2);
        assertThat(result.getUsersIds()).containsExactlyInAnyOrder("user-1", "user-2");

        verify(conversationRepository).saveConversation(any(Conversation.class));
        verify(userRepository, times(2)).updateUser(any(User.class));
    }

    @Test
    void execute_shouldAddConversationIdToUsers() {
        // Arrange
        when(userRepository.findUserById("user-1")).thenReturn(user1);
        when(userRepository.findUserById("user-2")).thenReturn(user2);

        // Act
        Conversation result = createConversationUseCase.execute(command);

        // Assert
        verify(userRepository, times(4)).findUserById(anyString());
        verify(userRepository, times(2)).updateUser(argThat(user ->
                user.getConversations().contains(result.getId())
        ));
    }
}
