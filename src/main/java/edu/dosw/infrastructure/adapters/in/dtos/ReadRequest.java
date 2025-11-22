package edu.dosw.infrastructure.adapters.in.dtos;

import lombok.Data;

@Data
public class ReadRequest {
    private String conversationId;
    private String messageId;
    private String userId;
}