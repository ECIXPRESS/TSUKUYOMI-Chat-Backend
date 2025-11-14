package edu.dosw.application;

import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.GetContactsUseCase;
import edu.dosw.domain.ports.outbound.UserRepository;

import java.util.List;

public class GetContactsService implements GetContactsUseCase {

    private final UserRepository userRepository;

    public GetContactsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> execute(String userId) {
        return userRepository.findUserById(userId).getContacts().stream()
                .map(s -> userRepository.findUserById(s))
                .filter(user -> user != null)
                .toList();
    }
}
