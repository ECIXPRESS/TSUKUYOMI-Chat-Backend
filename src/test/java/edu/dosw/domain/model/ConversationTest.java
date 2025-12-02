package edu.dosw.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConversationTest {

    private Conversation conversation;
    private List<String> userIds;
    private String orderId;

    @BeforeEach
    void setUp() {
        userIds = new ArrayList<>(Arrays.asList("user-1", "user-2"));
        orderId = "order-123";
        conversation = new Conversation(userIds, orderId);
    }

    @Test
    void constructor_shouldCreateConversationWithGeneratedId_whenUsingSimpleConstructor() {
        // Act
        Conversation result = new Conversation(userIds, orderId);

        // Assert
        assertThat(result.getId()).isNotNull();
        assertThat(result.getCreationDate()).isNotNull();
        assertThat(result.getUsersIds()).hasSize(2);
        assertThat(result.getMessages()).isEmpty();
        assertThat(result.getOrderId()).isEqualTo(orderId);
    }

    @Test
    void constructor_shouldCreateConversationWithProvidedData_whenUsingFullConstructor() {
        // Arrange
        String id = "conv-123";
        Instant creationDate = Instant.now();
        List<ConversationMessage> messages = new ArrayList<>();
        messages.add(new Regular("conv-123", "Hola", "user-1"));

        // Act
        Conversation result = new Conversation(id, creationDate, userIds, messages, orderId);

        // Assert
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getCreationDate()).isEqualTo(creationDate);
        assertThat(result.getUsersIds()).hasSize(2);
        assertThat(result.getMessages()).hasSize(1);
        assertThat(result.getOrderId()).isEqualTo(orderId);
    }

    @Test
    void addUser_shouldAddNewUser_whenUserDoesNotExist() {
        // Arrange
        String newUserId = "user-3";

        // Act
        conversation.addUser(newUserId);

        // Assert
        assertThat(conversation.getUsersIds()).contains(newUserId);
        assertThat(conversation.getUsersIds()).hasSize(3);
    }

    @Test
    void removeUser_shouldRemoveUser_whenUserExists() {
        // Arrange
        String userToRemove = "user-1";

        // Act
        conversation.removeUser(userToRemove);

        // Assert
        assertThat(conversation.getUsersIds()).doesNotContain(userToRemove);
        assertThat(conversation.getUsersIds()).hasSize(1);
    }

    @Test
    void removeUser_shouldDoNothing_whenUserDoesNotExist() {
        // Arrange
        String nonExistentUser = "user-999";
        int initialSize = conversation.getUsersIds().size();

        // Act
        conversation.removeUser(nonExistentUser);

        // Assert
        assertThat(conversation.getUsersIds()).hasSize(initialSize);
    }

    @Test
    void addMessage_shouldAddMessage_whenAuthorIsPartOfConversation() {
        // Arrange
        ConversationMessage message = new Regular(conversation.getId(), "Hola", "user-1");

        // Act
        conversation.addMessage(message);

        // Assert
        assertThat(conversation.getMessages()).hasSize(1);
        assertThat(conversation.getMessages()).contains(message);
    }

    @Test
    void addMessage_shouldNotAddMessage_whenAuthorIsNotPartOfConversation() {
        // Arrange
        ConversationMessage message = new Regular(conversation.getId(), "Hola", "user-999");
        int initialSize = conversation.getMessages().size();

        // Act
        conversation.addMessage(message);

        // Assert
        assertThat(conversation.getMessages()).hasSize(initialSize);
    }

    @Test
    void userSendMessage_shouldAddMessage_whenUserIsPartOfConversation() {
        // Arrange
        ConversationMessage message = new Regular(conversation.getId(), "Hola", "user-1");

        // Act
        conversation.userSendMessage(message);

        // Assert
        assertThat(conversation.getMessages()).hasSize(1);
        assertThat(conversation.getMessages()).contains(message);
    }

    @Test
    void userSendMessage_shouldNotAddMessage_whenUserIsNotPartOfConversation() {
        // Arrange
        ConversationMessage message = new Regular(conversation.getId(), "Hola", "user-999");
        int initialSize = conversation.getMessages().size();

        // Act
        conversation.userSendMessage(message);

        // Assert
        assertThat(conversation.getMessages()).hasSize(initialSize);
    }

    @Test
    void markMessageRead_shouldMarkMessageAsRead_whenMessageExists() {
        // Arrange
        ConversationMessage message = new Regular(conversation.getId(), "Hola", "user-1");
        conversation.addMessage(message);

        // Act
        conversation.markMessageRead(message);

        // Assert
        assertThat(message.getIsRead()).isTrue();
    }

    @Test
    void markMessageRead_shouldDoNothing_whenMessageDoesNotExist() {
        // Arrange
        ConversationMessage message = new Regular(conversation.getId(), "Hola", "user-1");

        // Act
        conversation.markMessageRead(message);

        // Assert
        assertThat(message.getIsRead()).isFalse();
    }

    @Test
    void getMessagesIds_shouldReturnAllMessageIds_whenMessagesExist() {
        // Arrange
        ConversationMessage message1 = new Regular(conversation.getId(), "Hola", "user-1");
        ConversationMessage message2 = new Regular(conversation.getId(), "Mundo", "user-2");
        conversation.addMessage(message1);
        conversation.addMessage(message2);

        // Act
        List<String> messageIds = conversation.getMessagesIds();

        // Assert
        assertThat(messageIds).hasSize(2);
        assertThat(messageIds).contains(message1.getId(), message2.getId());
    }

    @Test
    void getMessagesIds_shouldReturnEmptyList_whenNoMessagesExist() {
        // Act
        List<String> messageIds = conversation.getMessagesIds();

        // Assert
        assertThat(messageIds).isEmpty();
    }
}
