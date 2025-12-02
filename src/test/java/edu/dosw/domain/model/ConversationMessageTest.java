package edu.dosw.domain.model;

import edu.dosw.domain.model.exceptions.ModelLayerExceptions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ConversationMessageTest {

    @Test
    void constructor_shouldCreateMessage_whenValidDataProvided() {
        // Arrange
        String conversationId = "conv-123";
        String text = "Mensaje de prueba";
        String authorId = "user-123";

        // Act
        ConversationMessage result = new Regular(conversationId, text, authorId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getConversationId()).isEqualTo(conversationId);
        assertThat(result.getText()).isEqualTo(text);
        assertThat(result.getAuthor()).isEqualTo(authorId);
        assertThat(result.getSendDate()).isNotNull();
        assertThat(result.getIsRead()).isFalse();
    }

    @Test
    void constructor_shouldThrowException_whenTextIsEmpty() {
        // Arrange
        String conversationId = "conv-123";
        String emptyText = "";
        String authorId = "user-123";

        // Act & Assert
        assertThatThrownBy(() -> new Regular(conversationId, emptyText, authorId))
                .isInstanceOf(ModelLayerExceptions.class)
                .hasMessage("Cannot have empty message");
    }

    @Test
    void fullConstructor_shouldCreateMessage_whenValidDataProvided() {
        // Arrange
        String id = "msg-123";
        String conversationId = "conv-123";
        String authorId = "user-123";
        String text = "Mensaje existente";
        Instant sendDate = Instant.now().minusSeconds(3600);
        Boolean isRead = true;

        // Act
        ConversationMessage result = new TestConversationMessage(
                id, conversationId, authorId, text, sendDate, isRead
        );

        // Assert
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getConversationId()).isEqualTo(conversationId);
        assertThat(result.getAuthor()).isEqualTo(authorId);
        assertThat(result.getText()).isEqualTo(text);
        assertThat(result.getSendDate()).isEqualTo(sendDate);
        assertThat(result.getIsRead()).isEqualTo(isRead);
    }

    @Test
    void fullConstructor_shouldThrowException_whenTextIsEmpty() {
        // Arrange
        String id = "msg-123";
        String conversationId = "conv-123";
        String authorId = "user-123";
        String emptyText = "";
        Instant sendDate = Instant.now();
        Boolean isRead = false;

        // Act & Assert
        assertThatThrownBy(() -> new TestConversationMessage(
                id, conversationId, authorId, emptyText, sendDate, isRead
        ))
                .isInstanceOf(ModelLayerExceptions.class)
                .hasMessage("Cannot have empty message");
    }

    @Test
    void markRead_shouldSetIsReadToTrue_whenCalled() {
        // Arrange
        ConversationMessage message = new Regular("conv-123", "Texto", "user-123");
        assertThat(message.getIsRead()).isFalse();

        // Act
        message.markRead();

        // Assert
        assertThat(message.getIsRead()).isTrue();
    }

    @Test
    void markRead_shouldRemainTrue_whenCalledMultipleTimes() {
        // Arrange
        ConversationMessage message = new Regular("conv-123", "Texto", "user-123");

        // Act
        message.markRead();
        message.markRead();
        message.markRead();

        // Assert
        assertThat(message.getIsRead()).isTrue();
    }

    @Test
    void getAuthor_shouldReturnAuthorId_whenCalled() {
        // Arrange
        String authorId = "user-456";
        ConversationMessage message = new Regular("conv-123", "Texto", authorId);

        // Act
        String result = message.getAuthor();

        // Assert
        assertThat(result).isEqualTo(authorId);
    }

    @Test
    void getConversationId_shouldReturnConversationId_whenCalled() {
        // Arrange
        String conversationId = "conv-789";
        ConversationMessage message = new Regular(conversationId, "Texto", "user-123");

        // Act
        String result = message.getConversationId();

        // Assert
        assertThat(result).isEqualTo(conversationId);
    }

    @Test
    void getText_shouldReturnText_whenCalled() {
        // Arrange
        String text = "Este es el texto del mensaje";
        ConversationMessage message = new Regular("conv-123", text, "user-123");

        // Act
        String result = message.getText();

        // Assert
        assertThat(result).isEqualTo(text);
    }

    @Test
    void getId_shouldReturnUniqueId_whenCalled() {
        // Arrange & Act
        ConversationMessage message1 = new Regular("conv-123", "Texto 1", "user-123");
        ConversationMessage message2 = new Regular("conv-123", "Texto 2", "user-123");

        // Assert
        assertThat(message1.getId()).isNotNull();
        assertThat(message2.getId()).isNotNull();
        assertThat(message1.getId()).isNotEqualTo(message2.getId());
    }

    @Test
    void getSendDate_shouldReturnDateCloseToNow_whenMessageCreated() {
        // Arrange
        Instant before = Instant.now();

        // Act
        ConversationMessage message = new Regular("conv-123", "Texto", "user-123");
        Instant after = Instant.now();

        // Assert
        assertThat(message.getSendDate()).isNotNull();
        assertThat(message.getSendDate()).isBetween(before, after);
    }

    @Test
    void getIsRead_shouldReturnFalse_byDefault() {
        // Arrange & Act
        ConversationMessage message = new Regular("conv-123", "Texto", "user-123");

        // Assert
        assertThat(message.getIsRead()).isFalse();
    }

    @Test
    void constructor_shouldAllowWhitespaceInText_whenNotEmpty() {
        // Arrange
        String textWithSpaces = "   Texto con espacios   ";

        // Act
        ConversationMessage message = new Regular("conv-123", textWithSpaces, "user-123");

        // Assert
        assertThat(message.getText()).isEqualTo(textWithSpaces);
    }

    // Clase de prueba para acceder al constructor protegido
    private static class TestConversationMessage extends ConversationMessage {
        protected TestConversationMessage(String id, String conversationId, String author,
                                          String text, Instant sendDate, Boolean isRead) {
            super(id, conversationId, author, text, sendDate, isRead);
        }

        protected TestConversationMessage(String conversationId, String text, String author) {
            super(conversationId, text, author);
        }
    }
}
