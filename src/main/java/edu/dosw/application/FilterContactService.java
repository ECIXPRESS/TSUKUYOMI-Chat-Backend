package edu.dosw.application;

import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.FilterContactsUseCase;

import java.util.List;

public class FilterContactService implements FilterContactsUseCase {
    @Override
    public List<User> execute(String userId, String filter) {
        return List.of();
    }
}
