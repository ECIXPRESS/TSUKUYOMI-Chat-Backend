package edu.dosw.domain.ports.inbound.command;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AddUserToConversationCommand {
    private String userId;
}
