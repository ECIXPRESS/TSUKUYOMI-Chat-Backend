package edu.dosw.domain.ports.outbound;

import edu.dosw.domain.model.User;

public interface UserSyncExternalService {
    User getUserFromExternalService(String userId);
}
