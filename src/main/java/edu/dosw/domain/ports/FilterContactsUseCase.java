package edu.dosw.domain.ports;

import edu.dosw.domain.model.User;

import java.util.List;

public interface FilterContactsUseCase {
    List<User> execute(String userId, String filter);
}
