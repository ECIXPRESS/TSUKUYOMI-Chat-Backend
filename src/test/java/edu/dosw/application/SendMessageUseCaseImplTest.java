package edu.dosw.application;

import edu.dosw.application.applicationmappers.ConversationMessageApplicationMapper;
import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.model.Regular;
import edu.dosw.domain.ports.inbound.command.SendConversationMessageCommand;
import edu.dosw.domain.ports.outbound.ConversationMessageRepository;
import edu.dosw.domain.ports.outbound.ConversationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendMessageUseCaseImplTest {

    @Mock
    private ConversationRepository conversationRepository;

    @Mock
    private ConversationMessageApplicationMapper conversationMessageApplicationMapper;

    @Mock
    private ConversationMessageRepository conversationMessageRepository;

    @InjectMocks
    private SendMessageUseCaseImpl sendMessageUseCase;

    private String conversationId;
    private String authorId;
    private Conversation conversation;
    private SendConversationMessageCommand command;

    @BeforeEach
    void setUp() {
        conversationId = "conv-123";
        authorId = "user-1";

        List<String> userIds = Arrays.asList(authorId, "user-2");
        conversation = new Conversation(userIds, "order-1");

        command = new SendConversationMessageCommand(conversationId, "Hola mundo", authorId);
    }

    @Test
    void execute_shouldSendMessage_whenConversationExistsAndUserIsAuthorized() {
        // Arrange
        ConversationMessage message = new Regular(conversationId, "Hola mundo", authorId);

        when(conversationRepository.findConversationById(conversationId)).thenReturn(conversation);
        when(conversationMessageApplicationMapper.toDomain(command)).thenReturn(message);

        // Act
        ConversationMessage result = sendMessageUseCase.execute(command);

        // Assert
        assertThat(result).isNotNull();
        verify(conversationRepository).updateConversation(conversation);
        verify(conversationMessageRepository).saveConversationMessage(message);
    }

    @Test
    void execute_shouldThrowException_whenConversationNotFound() {
        // Arrange
        when(conversationRepository.findConversationById(conversationId)).thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> sendMessageUseCase.execute(command))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining(conversationId);

        verify(conversationRepository, never()).updateConversation(any());
        verify(conversationMessageRepository, never()).saveConversationMessage(any());
    }

    @Test
    void execute_shouldThrowException_whenUserIsNotAuthorized() {
        // Arrange
        String unauthorizedUserId = "user-999";
        SendConversationMessageCommand unauthorizedCommand = new SendConversationMessageCommand(
                conversationId, "Mensaje no autorizado", unauthorizedUserId
        );
        ConversationMessage message = new Regular(conversationId, "Mensaje no autorizado", unauthorizedUserId);

        when(conversationRepository.findConversationById(conversationId)).thenReturn(conversation);
        when(conversationMessageApplicationMapper.toDomain(unauthorizedCommand)).thenReturn(message);

        // Act & Assert
        assertThatThrownBy(() -> sendMessageUseCase.execute(unauthorizedCommand))
                .isInstanceOf(RuntimeException.class);

        verify(conversationRepository, never()).updateConversation(any());
        verify(conversationMessageRepository, never()).saveConversationMessage(any());
    }
}
