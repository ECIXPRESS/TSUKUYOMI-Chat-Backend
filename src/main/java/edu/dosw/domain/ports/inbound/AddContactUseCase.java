package edu.dosw.domain.ports.inbound;

import edu.dosw.domain.ports.inbound.command.AddContactCommand;

public interface AddContactUseCase {
    void execute(AddContactCommand command);
}
