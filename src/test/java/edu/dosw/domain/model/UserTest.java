package edu.dosw.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserTest {

    private User user;
    private String userId;
    private String userName;
    private String profilePhoto;

    @BeforeEach
    void setUp() {
        userId = "user-123";
        userName = "Juan";
        profilePhoto = "https://example.com/photo.jpg";
        user = new User(userId, userName, profilePhoto);
    }

    @Test
    void constructor_shouldCreateUserWithEmptyLists_whenUsingSimpleConstructor() {
        // Act
        User result = new User(userId, userName, profilePhoto);

        // Assert
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getName()).isEqualTo(userName);
        assertThat(result.getProfilePhoto()).isEqualTo(profilePhoto);
        assertThat(result.getConversations()).isEmpty();
        assertThat(result.getContacts()).isEmpty();
        assertThat(result.getIsActive()).isFalse();
    }

    @Test
    void constructor_shouldCreateUserWithProvidedLists_whenUsingFullConstructor() {
        // Arrange
        List<String> conversations = new ArrayList<>(Arrays.asList("conv-1", "conv-2"));
        List<String> contacts = new ArrayList<>(Arrays.asList("user-1", "user-2"));

        // Act
        User result = new User(userId, userName, profilePhoto, conversations, contacts);

        // Assert
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getName()).isEqualTo(userName);
        assertThat(result.getProfilePhoto()).isEqualTo(profilePhoto);
        assertThat(result.getConversations()).hasSize(2);
        assertThat(result.getContacts()).hasSize(2);
    }

    @Test
    void addConversation_shouldAddConversationId_whenCalled() {
        // Arrange
        String conversationId = "conv-123";

        // Act
        user.addConversation(conversationId);

        // Assert
        assertThat(user.getConversations()).contains(conversationId);
        assertThat(user.getConversations()).hasSize(1);
    }

    @Test
    void addConversation_shouldAllowDuplicates_whenSameConversationAddedTwice() {
        // Arrange
        String conversationId = "conv-123";

        // Act
        user.addConversation(conversationId);
        user.addConversation(conversationId);

        // Assert
        assertThat(user.getConversations()).hasSize(2);
    }

    @Test
    void removeConversation_shouldRemoveConversation_whenConversationExists() {
        // Arrange
        String conversationId = "conv-123";
        user.addConversation(conversationId);

        // Act
        user.removeConversation(conversationId);

        // Assert
        assertThat(user.getConversations()).doesNotContain(conversationId);
        assertThat(user.getConversations()).isEmpty();
    }

    @Test
    void removeConversation_shouldDoNothing_whenConversationDoesNotExist() {
        // Arrange
        String conversationId = "conv-123";
        user.addConversation("conv-456");
        int initialSize = user.getConversations().size();

        // Act
        user.removeConversation(conversationId);

        // Assert
        assertThat(user.getConversations()).hasSize(initialSize);
    }

    @Test
    void removeConversation_shouldRemoveAllOccurrences_whenDuplicatesExist() {
        // Arrange
        String conversationId = "conv-123";
        user.addConversation(conversationId);
        user.addConversation(conversationId);
        user.addConversation("conv-456");

        // Act
        user.removeConversation(conversationId);

        // Assert
        assertThat(user.getConversations()).doesNotContain(conversationId);
        assertThat(user.getConversations()).hasSize(1);
        assertThat(user.getConversations()).contains("conv-456");
    }

    @Test
    void setName_shouldUpdateName_whenCalled() {
        // Arrange
        String newName = "Pedro";

        // Act
        user.setName(newName);

        // Assert
        assertThat(user.getName()).isEqualTo(newName);
    }

    @Test
    void setProfilePhoto_shouldUpdateProfilePhoto_whenCalled() {
        // Arrange
        String newPhoto = "https://example.com/new-photo.jpg";

        // Act
        user.setProfilePhoto(newPhoto);

        // Assert
        assertThat(user.getProfilePhoto()).isEqualTo(newPhoto);
    }

    @Test
    void getContacts_shouldReturnEmptyList_whenNoContactsAdded() {
        // Act
        List<String> contacts = user.getContacts();

        // Assert
        assertThat(contacts).isEmpty();
    }

    @Test
    void getContacts_shouldReturnContactsList_whenContactsExist() {
        // Arrange
        User userWithContacts = new User(
                userId,
                userName,
                profilePhoto,
                new ArrayList<>(),
                new ArrayList<>(Arrays.asList("contact-1", "contact-2"))
        );

        // Act
        List<String> contacts = userWithContacts.getContacts();

        // Assert
        assertThat(contacts).hasSize(2);
        assertThat(contacts).contains("contact-1", "contact-2");
    }

    @Test
    void getIsActive_shouldReturnFalse_byDefault() {
        // Act & Assert
        assertThat(user.getIsActive()).isFalse();
    }
}
