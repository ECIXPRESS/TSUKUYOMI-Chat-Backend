package edu.dosw.infrastructure.adapters.in.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CreateConversationRequest {
    private List<String> usersIds;
}
