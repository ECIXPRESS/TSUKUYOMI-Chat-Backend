package edu.dosw.domain.ports;

import edu.dosw.domain.model.User;

import java.util.List;

public interface GetContactsUseCase {
    List<User> execute(String userId);
}
