package edu.dosw.infrastructure.adapters.in.mappers;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.model.Regular;
import edu.dosw.domain.ports.inbound.command.CreateConversationCommand;
import edu.dosw.domain.ports.inbound.command.DeleteConversationCommand;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationResponse;
import edu.dosw.infrastructure.adapters.in.dtos.CreateConversationRequest;
import edu.dosw.infrastructure.adapters.in.dtos.DeleteConversationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ConversationWebMapperTest {

    private ConversationWebMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ConversationWebMapper();
    }

    @Test
    void toCommand_shouldMapCreateRequestToCommand_whenValidDataProvided() {
        // Arrange
        CreateConversationRequest request = new CreateConversationRequest();
        request.setUsersIds(Arrays.asList("user-1", "user-2", "user-3"));
        request.setOrderId("order-789");

        // Act
        CreateConversationCommand result = mapper.toCommand(request);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUsersIds()).containsExactlyInAnyOrder("user-1", "user-2", "user-3");
        assertThat(result.getOrderId()).isEqualTo("order-789");
    }

    @Test
    void toCommand_shouldHandleEmptyUsersList_whenRequestHasEmptyList() {
        // Arrange
        CreateConversationRequest request = new CreateConversationRequest();
        request.setUsersIds(new ArrayList<>());
        request.setOrderId("order-123");

        // Act
        CreateConversationCommand result = mapper.toCommand(request);

        // Assert
        assertThat(result.getUsersIds()).isEmpty();
    }

    @Test
    void toCommand_shouldHandleNullOrderId_whenRequestHasNullOrderId() {
        // Arrange
        CreateConversationRequest request = new CreateConversationRequest();
        request.setUsersIds(Arrays.asList("user-1"));
        request.setOrderId(null);

        // Act
        CreateConversationCommand result = mapper.toCommand(request);

        // Assert
        assertThat(result.getOrderId()).isNull();
    }

}
