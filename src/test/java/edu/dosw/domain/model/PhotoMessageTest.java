package edu.dosw.domain.model;

import edu.dosw.domain.model.exceptions.ModelLayerExceptions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PhotoMessageTest {

    @Test
    void constructor_shouldCreatePhotoMessage_whenValidDataProvided() {
        // Arrange
        String conversationId = "conv-123";
        String photoUrl = "https://example.com/photo.jpg";
        String authorId = "user-123";

        // Act
        PhotoMessage result = new PhotoMessage(conversationId, photoUrl, authorId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isNotNull();
        assertThat(result.getConversationId()).isEqualTo(conversationId);
        assertThat(result.getText()).isEqualTo(photoUrl);
        assertThat(result.getAuthor()).isEqualTo(authorId);
        assertThat(result.getSendDate()).isNotNull();
        assertThat(result.getIsRead()).isFalse();
    }

    @Test
    void constructor_shouldThrowException_whenPhotoUrlIsEmpty() {
        // Arrange
        String conversationId = "conv-123";
        String emptyUrl = "";
        String authorId = "user-123";

        // Act & Assert
        assertThatThrownBy(() -> new PhotoMessage(conversationId, emptyUrl, authorId))
                .isInstanceOf(ModelLayerExceptions.class)
                .hasMessage("Cannot have empty message");
    }

    @Test
    void constructor_shouldCreateMessage_whenUrlHasWhitespace() {
        // Arrange
        String conversationId = "conv-123";
        String urlWithSpaces = "   https://example.com/photo.jpg   ";
        String authorId = "user-123";

        // Act
        PhotoMessage result = new PhotoMessage(conversationId, urlWithSpaces, authorId);

        // Assert
        assertThat(result.getText()).isEqualTo(urlWithSpaces);
    }

    @Test
    void markRead_shouldChangeIsReadToTrue_whenCalled() {
        // Arrange
        PhotoMessage message = new PhotoMessage("conv-123", "https://example.com/photo.jpg", "user-123");
        assertThat(message.getIsRead()).isFalse();

        // Act
        message.markRead();

        // Assert
        assertThat(message.getIsRead()).isTrue();
    }

    @Test
    void markRead_shouldRemainTrue_whenCalledMultipleTimes() {
        // Arrange
        PhotoMessage message = new PhotoMessage("conv-123", "https://example.com/photo.jpg", "user-123");

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
        PhotoMessage message1 = new PhotoMessage("conv-123", "https://example.com/photo1.jpg", "user-123");
        PhotoMessage message2 = new PhotoMessage("conv-123", "https://example.com/photo2.jpg", "user-123");

        // Assert
        assertThat(message1.getId()).isNotEqualTo(message2.getId());
    }

    @Test
    void getSendDate_shouldReturnNonNullDate_whenMessageCreated() {
        // Arrange & Act
        PhotoMessage message = new PhotoMessage("conv-123", "https://example.com/photo.jpg", "user-123");

        // Assert
        assertThat(message.getSendDate()).isNotNull();
        assertThat(message.getSendDate()).isBeforeOrEqualTo(java.time.Instant.now());
    }

    @Test
    void photoMessage_shouldInheritFromConversationMessage_whenCreated() {
        // Arrange & Act
        PhotoMessage message = new PhotoMessage("conv-123", "https://example.com/photo.jpg", "user-123");

        // Assert
        assertThat(message).isInstanceOf(ConversationMessage.class);
    }
}
