package edu.dosw.infrastructure.adapters.in.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ConversationMessageResponse {
    private String messageId;
    private String conversationId;
    private String authorId;
    private String content;
    private Instant creationDate;
    private Boolean isRead;
}
