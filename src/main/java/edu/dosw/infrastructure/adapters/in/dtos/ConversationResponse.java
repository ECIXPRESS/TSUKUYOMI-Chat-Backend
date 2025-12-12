package edu.dosw.infrastructure.adapters.in.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@Data
public class ConversationResponse {
    private String conversationId;
    private Instant creationDate;
    private List<String> usersIds;
    private List<ConversationMessageResponse> messageResponses;
    private String orderId;
}
