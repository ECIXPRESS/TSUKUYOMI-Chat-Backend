package edu.dosw.domain.ports.inbound.command;


import lombok.Data;

import java.util.List;

@Data
public class CreateConversationCommand {
    private List<String> usersIds;
}
