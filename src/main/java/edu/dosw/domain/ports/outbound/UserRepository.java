package edu.dosw.domain.ports.outbound;

import edu.dosw.domain.model.User;

import java.util.List;

public interface UserRepository {
    void saveUser(User user);
    List<User> listUsers();
    void deleteUser(String userId);
    User findUserById(String userId);
}
