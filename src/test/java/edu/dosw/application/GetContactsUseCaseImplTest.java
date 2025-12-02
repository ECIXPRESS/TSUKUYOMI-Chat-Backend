package edu.dosw.application;

import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.outbound.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetContactsUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private GetContactsUseCaseImpl getContactsUseCase;

    private User mainUser;
    private User contact1;
    private User contact2;
    private User contact3;

    @BeforeEach
    void setUp() {
        mainUser = new User("user-1", "Juan", null);
        contact1 = new User("user-2", "Pedro", null);
        contact2 = new User("user-3", "Maria", null);
        contact3 = new User("user-4", "Carlos", null);

        mainUser.getContacts().addAll(Arrays.asList("user-2", "user-3", "user-4"));
    }

    @Test
    void execute_shouldReturnAllContacts_whenUserHasContacts() {
        // Arrange
        when(userRepository.findUserById("user-1")).thenReturn(mainUser);
        when(userRepository.findUserById("user-2")).thenReturn(contact1);
        when(userRepository.findUserById("user-3")).thenReturn(contact2);
        when(userRepository.findUserById("user-4")).thenReturn(contact3);

        // Act
        List<User> result = getContactsUseCase.execute("user-1");

        // Assert
        assertThat(result).hasSize(3);
        assertThat(result).extracting(User::getId)
                .containsExactlyInAnyOrder("user-2", "user-3", "user-4");
    }

    @Test
    void execute_shouldReturnEmptyList_whenUserHasNoContacts() {
        // Arrange
        User userWithoutContacts = new User("user-5", "Ana", null);
        when(userRepository.findUserById("user-5")).thenReturn(userWithoutContacts);

        // Act
        List<User> result = getContactsUseCase.execute("user-5");

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void execute_shouldFilterOutNullContacts_whenSomeContactsDontExist() {
        // Arrange
        when(userRepository.findUserById("user-1")).thenReturn(mainUser);
        when(userRepository.findUserById("user-2")).thenReturn(contact1);
        when(userRepository.findUserById("user-3")).thenReturn(null);
        when(userRepository.findUserById("user-4")).thenReturn(contact3);

        // Act
        List<User> result = getContactsUseCase.execute("user-1");

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).extracting(User::getId)
                .containsExactlyInAnyOrder("user-2", "user-4");
    }

    @Test
    void execute_shouldReturnEmptyList_whenAllContactsAreNull() {
        // Arrange
        when(userRepository.findUserById("user-1")).thenReturn(mainUser);
        when(userRepository.findUserById("user-2")).thenReturn(null);
        when(userRepository.findUserById("user-3")).thenReturn(null);
        when(userRepository.findUserById("user-4")).thenReturn(null);

        // Act
        List<User> result = getContactsUseCase.execute("user-1");

        // Assert
        assertThat(result).isEmpty();
    }
}
