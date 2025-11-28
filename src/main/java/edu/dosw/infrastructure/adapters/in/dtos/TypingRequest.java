package edu.dosw.infrastructure.adapters.in.dtos;

import lombok.Data;

@Data
public class TypingRequest {
    private String conversationId;
    private String userId;
    private boolean isTyping;
}