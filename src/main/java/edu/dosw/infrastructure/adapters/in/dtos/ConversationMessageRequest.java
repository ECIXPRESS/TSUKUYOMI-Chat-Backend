package edu.dosw.infrastructure.adapters.in.dtos;

import lombok.Data;

@Data
public class ConversationMessageRequest {
    private String authorId;
    private String conversationId;
    private String text;
}
