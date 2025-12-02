package edu.dosw.application;

import edu.dosw.application.exceptions.ApplicationLayerExceptions;
import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.command.AddContactCommand;
import edu.dosw.domain.ports.outbound.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddContactUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AddContactUseCaseImpl addContactUseCase;

    private User user;
    private User contact;
    private AddContactCommand command;

    @BeforeEach
    void setUp() {
        user = new User("user-1", "Juan", null);
        contact = new User("user-2", "Pedro", null);
        command = new AddContactCommand("user-1", "user-2");
    }

    @Test
    void execute_shouldAddContactBidirectionally_whenBothUsersExist() {
        // Arrange
        when(userRepository.findUserById("user-1")).thenReturn(user);
        when(userRepository.findUserById("user-2")).thenReturn(contact);

        // Act
        addContactUseCase.execute(command);

        // Assert
        verify(userRepository, times(2)).updateUser(any(User.class));
        verify(userRepository).updateUser(argThat(u ->
                u.getId().equals("user-1") && u.getContacts().contains("user-2")
        ));
        verify(userRepository).updateUser(argThat(u ->
                u.getId().equals("user-2") && u.getContacts().contains("user-1")
        ));
    }

    @Test
    void execute_shouldThrowException_whenUserNotFound() {
        // Arrange
        when(userRepository.findUserById("user-1")).thenReturn(null);
        when(userRepository.findUserById("user-2")).thenReturn(contact);

        // Act & Assert
        assertThatThrownBy(() -> addContactUseCase.execute(command))
                .isInstanceOf(RuntimeException.class);

        verify(userRepository, never()).updateUser(any());
    }

    @Test
    void execute_shouldThrowException_whenContactNotFound() {
        // Arrange
        when(userRepository.findUserById("user-1")).thenReturn(user);
        when(userRepository.findUserById("user-2")).thenReturn(null);

        // Act & Assert
        assertThatThrownBy(() -> addContactUseCase.execute(command))
                .isInstanceOf(RuntimeException.class);

        verify(userRepository, never()).updateUser(any());
    }

    @Test
    void execute_shouldNotDuplicateContact_whenContactAlreadyExists() {
        // Arrange
        user.getContacts().add("user-2");
        contact.getContacts().add("user-1");

        when(userRepository.findUserById("user-1")).thenReturn(user);
        when(userRepository.findUserById("user-2")).thenReturn(contact);

        // Act
        addContactUseCase.execute(command);

        // Assert
        verify(userRepository, never()).updateUser(any());
    }

    @Test
    void execute_shouldAddContactOnlyToUserWithoutIt() {
        // Arrange
        user.getContacts().add("user-2");

        when(userRepository.findUserById("user-1")).thenReturn(user);
        when(userRepository.findUserById("user-2")).thenReturn(contact);

        // Act
        addContactUseCase.execute(command);

        // Assert
        verify(userRepository, times(1)).updateUser(contact);
        verify(userRepository, never()).updateUser(user);
    }
}
