package edu.dosw.application;

import edu.dosw.application.exceptions.ApplicationLayerExceptions;
import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.model.Regular;
import edu.dosw.domain.ports.outbound.ConversationRepository;
import edu.dosw.infrastructure.persistence.mongodb.exceptions.MongoPersistenceExceptions;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilterMessagesUseCaseImplTest {

    @Mock
    private ConversationRepository conversationRepository;

    @InjectMocks
    private FilterMessagesUseCaseImpl filterMessagesUseCase;

    private Conversation conversation;
    private ConversationMessage message1;
    private ConversationMessage message2;
    private ConversationMessage message3;

    @BeforeEach
    void setUp() {
        List<String> userIds = Arrays.asList("user-1", "user-2");
        conversation = new Conversation(userIds, "order-123");

        message1 = new Regular(conversation.getId(), "Hola, ¿cómo estás?", "user-1");
        message2 = new Regular(conversation.getId(), "Bien, gracias", "user-2");
        message3 = new Regular(conversation.getId(), "¿Cuándo llega el pedido?", "user-1");

        conversation.addMessage(message1);
        conversation.addMessage(message2);
        conversation.addMessage(message3);
    }

    @Test
    void execute_shouldReturnAllMessages_whenFilterWordIsNull() {
        // Arrange
        when(conversationRepository.findConversationById(conversation.getId())).thenReturn(conversation);

        // Act
        List<ConversationMessage> result = filterMessagesUseCase.execute(conversation.getId(), null);

        // Assert
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(message1, message2, message3);
    }

    @Test
    void execute_shouldReturnAllMessages_whenFilterWordIsEmpty() {
        // Arrange
        when(conversationRepository.findConversationById(conversation.getId())).thenReturn(conversation);

        // Act
        List<ConversationMessage> result = filterMessagesUseCase.execute(conversation.getId(), "   ");

        // Assert
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(message1, message2, message3);
    }

    @Test
    void execute_shouldReturnFilteredMessages_whenFilterWordMatches() {
        // Arrange
        when(conversationRepository.findConversationById(conversation.getId())).thenReturn(conversation);

        // Act
        List<ConversationMessage> result = filterMessagesUseCase.execute(conversation.getId(), "pedido");

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getText()).contains("pedido");
    }

    @Test
    void execute_shouldReturnEmptyList_whenNoMessagesMatchFilter() {
        // Arrange
        when(conversationRepository.findConversationById(conversation.getId())).thenReturn(conversation);

        // Act
        List<ConversationMessage> result = filterMessagesUseCase.execute(conversation.getId(), "xyz123");

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void execute_shouldThrowException_whenConversationNotFound() {
        // Arrange
        when(conversationRepository.findConversationById("invalid-id"))
                .thenThrow(MongoPersistenceExceptions.conversationNotFound());

        // Act & Assert
        assertThatThrownBy(() -> filterMessagesUseCase.execute("invalid-id", "test"))
                .isInstanceOf(RuntimeException.class);
    }

    @Test
    void execute_shouldReturnMultipleMessages_whenFilterWordMatchesMultiple() {
        // Arrange
        when(conversationRepository.findConversationById(conversation.getId())).thenReturn(conversation);

        // Act
        List<ConversationMessage> result = filterMessagesUseCase.execute(conversation.getId(), "Hola");

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getText()).isEqualTo("Hola, ¿cómo estás?");
    }
}
