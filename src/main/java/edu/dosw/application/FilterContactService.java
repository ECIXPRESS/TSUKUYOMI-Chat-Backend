package edu.dosw.application;

import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.FilterContactsUseCase;
import edu.dosw.domain.ports.outbound.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

public class FilterContactService implements FilterContactsUseCase {

    private final UserRepository userRepository;

    public FilterContactService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> execute(String userId, String filterWord) {
        return userRepository.findUserById(userId)
                .getContacts()
                .stream()
                .map(contactId -> userRepository.findUserById(contactId))
                .filter(user -> user != null)
                .filter(user -> user.getName().contains(filterWord))
                .collect(Collectors.toList());
    }

}
