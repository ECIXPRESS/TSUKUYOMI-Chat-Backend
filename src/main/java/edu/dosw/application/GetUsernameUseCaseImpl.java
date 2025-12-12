package edu.dosw.application;

import edu.dosw.domain.ports.inbound.GetUsernameUseCase;
import edu.dosw.domain.ports.outbound.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GetUsernameUseCaseImpl  implements GetUsernameUseCase {

    private final UserRepository userRepository;

    public GetUsernameUseCaseImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String execute(String userId) {
        return userRepository.findUserById(userId).getName();
    }
}
