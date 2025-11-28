package edu.dosw.domain.ports.inbound.command;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CreateConversationCommand {
    private List<String> usersIds;
    private String orderId;
}
