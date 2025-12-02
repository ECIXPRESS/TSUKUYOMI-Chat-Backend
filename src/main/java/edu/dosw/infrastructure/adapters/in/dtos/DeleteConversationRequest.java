package edu.dosw.infrastructure.adapters.in.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteConversationRequest {
    private String userId;
    private String conversationId;
}
