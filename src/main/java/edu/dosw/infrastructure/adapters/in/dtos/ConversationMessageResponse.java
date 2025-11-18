package edu.dosw.infrastructure.adapters.in.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConversationMessageResponse {
    private String messageId;
    private String conversationId;
    private String authorId;
    private String content;
    private long timestamp;
}
