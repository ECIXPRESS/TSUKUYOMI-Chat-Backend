package edu.dosw.application;

import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.GetContactsUseCase;
import edu.dosw.domain.ports.outbound.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class GetContactsUseCaseImpl implements GetContactsUseCase {

    private final UserRepository userRepository;

    public GetContactsUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> execute(String userId) {
        return userRepository.findUserById(userId).getContacts().stream()
                .map(userRepository::findUserById)
                .filter(Objects::nonNull)
                .toList();
    }
}
