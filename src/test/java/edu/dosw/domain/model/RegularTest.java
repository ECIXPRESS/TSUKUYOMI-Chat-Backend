package edu.dosw.domain.model;

import edu.dosw.domain.model.exceptions.ModelLayerExceptions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class RegularTest {

    @Test
    void constructor_shouldCreateRegularMessage_whenValidDataProvided() {
        // Arrange
        String conversationId = "conv-123";
        String text = "Hola mundo";
        String authorId = "user-123";

        // Act
        Regular result = new Regular(conversationId, text, authorId);

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
    void constructor_shouldCreateMessage_whenTextHasWhitespace() {
        // Arrange
        String conversationId = "conv-123";
        String textWithSpaces = "   Hola   ";
        String authorId = "user-123";

        // Act
        Regular result = new Regular(conversationId, textWithSpaces, authorId);

        // Assert
        assertThat(result.getText()).isEqualTo(textWithSpaces);
    }

    @Test
    void markRead_shouldChangeIsReadToTrue_whenCalled() {
        // Arrange
        Regular message = new Regular("conv-123", "Hola", "user-123");
        assertThat(message.getIsRead()).isFalse();

        // Act
        message.markRead();

        // Assert
        assertThat(message.getIsRead()).isTrue();
    }

    @Test
    void markRead_shouldRemainTrue_whenCalledMultipleTimes() {
        // Arrange
        Regular message = new Regular("conv-123", "Hola", "user-123");

        // Act
        message.markRead();
        message.markRead();
        message.markRead();

        // Assert
        assertThat(message.getIsRead()).isTrue();
    }

    @Test
    void getId_shouldReturnUniqueIds_whenMultipleMessagesCreated() {
        // Arrange & Act
        Regular message1 = new Regular("conv-123", "Mensaje 1", "user-123");
        Regular message2 = new Regular("conv-123", "Mensaje 2", "user-123");

        // Assert
        assertThat(message1.getId()).isNotEqualTo(message2.getId());
    }

    @Test
    void getSendDate_shouldReturnNonNullDate_whenMessageCreated() {
        // Arrange & Act
        Regular message = new Regular("conv-123", "Hola", "user-123");

        // Assert
        assertThat(message.getSendDate()).isNotNull();
        assertThat(message.getSendDate()).isBeforeOrEqualTo(java.time.Instant.now());
    }
}
