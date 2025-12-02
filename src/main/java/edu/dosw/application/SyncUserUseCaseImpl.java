package edu.dosw.application;

import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.SyncUserUseCase;
import edu.dosw.domain.ports.outbound.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class SyncUserUseCaseImpl implements SyncUserUseCase {
    private final UserRepository userRepository;

    @Override
    public User execute(String userId, String name) {
        User user = userRepository.findUserById(userId);
        if(user == null){
            user = new User(userId,name,null);
            userRepository.saveUser(user);
            return user;
        }
        return user;
    }
}