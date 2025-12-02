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
class FilterContactUseCaseImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private FilterContactUseCaseImpl filterContactUseCase;

    private User mainUser;
    private User contact1;
    private User contact2;
    private User contact3;

    @BeforeEach
    void setUp() {
        mainUser = new User("user-1", "Juan", null);
        contact1 = new User("user-2", "Pedro Martinez", null);
        contact2 = new User("user-3", "Maria Lopez", null);
        contact3 = new User("user-4", "Carlos Perez", null);

        mainUser.getContacts().addAll(Arrays.asList("user-2", "user-3", "user-4"));
    }

    @Test
    void execute_shouldReturnAllContacts_whenFilterWordMatchesMultiple() {
        // Arrange
        when(userRepository.findUserById("user-1")).thenReturn(mainUser);
        when(userRepository.findUserById("user-2")).thenReturn(contact1);
        when(userRepository.findUserById("user-3")).thenReturn(contact2);
        when(userRepository.findUserById("user-4")).thenReturn(contact3);

        // Act
        List<User> result = filterContactUseCase.execute("user-1", "Pe");

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).extracting(User::getName)
                .containsExactlyInAnyOrder("Pedro Martinez", "Carlos Perez");
    }

    @Test
    void execute_shouldReturnFilteredContacts_whenFilterWordMatchesOne() {
        // Arrange
        when(userRepository.findUserById("user-1")).thenReturn(mainUser);
        when(userRepository.findUserById("user-2")).thenReturn(contact1);
        when(userRepository.findUserById("user-3")).thenReturn(contact2);
        when(userRepository.findUserById("user-4")).thenReturn(contact3);

        // Act
        List<User> result = filterContactUseCase.execute("user-1", "Maria");

        // Assert
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getName()).isEqualTo("Maria Lopez");
    }

    @Test
    void execute_shouldReturnEmptyList_whenNoContactsMatchFilter() {
        // Arrange
        when(userRepository.findUserById("user-1")).thenReturn(mainUser);
        when(userRepository.findUserById("user-2")).thenReturn(contact1);
        when(userRepository.findUserById("user-3")).thenReturn(contact2);
        when(userRepository.findUserById("user-4")).thenReturn(contact3);

        // Act
        List<User> result = filterContactUseCase.execute("user-1", "XYZ");

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    void execute_shouldFilterOutNullContacts() {
        // Arrange
        when(userRepository.findUserById("user-1")).thenReturn(mainUser);
        when(userRepository.findUserById("user-2")).thenReturn(contact1);
        when(userRepository.findUserById("user-3")).thenReturn(null);
        when(userRepository.findUserById("user-4")).thenReturn(contact3);

        // Act
        List<User> result = filterContactUseCase.execute("user-1", "Pe");

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result).extracting(User::getName)
                .containsExactlyInAnyOrder("Pedro Martinez", "Carlos Perez");
    }

    @Test
    void execute_shouldReturnEmptyList_whenUserHasNoContacts() {
        // Arrange
        User userWithoutContacts = new User("user-5", "Ana", null);
        when(userRepository.findUserById("user-5")).thenReturn(userWithoutContacts);

        // Act
        List<User> result = filterContactUseCase.execute("user-5", "test");

        // Assert
        assertThat(result).isEmpty();
    }
}
