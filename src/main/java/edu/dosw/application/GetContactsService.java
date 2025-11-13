package edu.dosw.application;

import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.GetContactsUseCase;

import java.util.List;

public class GetContactsService implements GetContactsUseCase {
    @Override
    public List<User> execute(String userId) {
        return List.of();
    }
}
