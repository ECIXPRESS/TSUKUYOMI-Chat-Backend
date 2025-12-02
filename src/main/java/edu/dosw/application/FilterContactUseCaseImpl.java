package edu.dosw.application;

import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.FilterContactsUseCase;
import edu.dosw.domain.ports.outbound.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FilterContactUseCaseImpl implements FilterContactsUseCase {

    private final UserRepository userRepository;

    public FilterContactUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> execute(String userId, String filterWord) {
        return userRepository.findUserById(userId)
                .getContacts()
                .stream()
                .map(userRepository::findUserById)
                .filter(Objects::nonNull)
                .filter(user -> user.getName().contains(filterWord))
                .collect(Collectors.toList());
    }

}
