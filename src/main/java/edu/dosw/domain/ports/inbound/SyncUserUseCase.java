package edu.dosw.domain.ports.inbound;

import edu.dosw.domain.model.User;

public interface SyncUserUseCase {
    User execute(String email, String name);
}
