package edu.dosw.infrastructure.adapters.in.mappers;

import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.model.Regular;
import edu.dosw.domain.ports.inbound.command.SendConversationMessageCommand;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationMessageRequest;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationMessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ConversationMessageWebMapperTest {

    private ConversationMessageWebMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ConversationMessageWebMapper();
    }

    @Test
    void toCommand_shouldMapRequestToCommand_whenValidDataProvided() {
        // Arrange
        ConversationMessageRequest request = new ConversationMessageRequest();
        request.setConversationId("conv-123");
        request.setAuthorId("user-123");
        request.setText("Hola mundo");

        // Act
        SendConversationMessageCommand result = mapper.toCommand(request);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getConversationId()).isEqualTo("conv-123");
        assertThat(result.getAuthorId()).isEqualTo("user-123");
        assertThat(result.getText()).isEqualTo("Hola mundo");
    }

    @Test
    void toCommand_shouldHandleEmptyText_whenRequestHasEmptyText() {
        // Arrange
        ConversationMessageRequest request = new ConversationMessageRequest();
        request.setConversationId("conv-123");
        request.setAuthorId("user-123");
        request.setText("");

        // Act
        SendConversationMessageCommand result = mapper.toCommand(request);

        // Assert
        assertThat(result.getText()).isEmpty();
    }


    @Test
    void toResponse_shouldMapReadMessage_whenMessageIsMarkedAsRead() {
        // Arrange
        ConversationMessage message = new Regular("conv-123", "Mensaje leído", "user-789");
        message.markRead();

        // Act
        ConversationMessageResponse result = mapper.toResponse(message);

        // Assert
        assertThat(result.getIsRead()).isTrue();
    }



    @Test
    void toCommand_shouldHandleNullFields_whenRequestHasNulls() {
        // Arrange
        ConversationMessageRequest request = new ConversationMessageRequest();
        request.setConversationId(null);
        request.setAuthorId(null);
        request.setText(null);

        // Act
        SendConversationMessageCommand result = mapper.toCommand(request);

        // Assert
        assertThat(result.getConversationId()).isNull();
        assertThat(result.getAuthorId()).isNull();
        assertThat(result.getText()).isNull();
    }
}
