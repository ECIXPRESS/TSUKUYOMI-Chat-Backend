package edu.dosw.infrastructure.adapters.in.mappers;

import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.command.AddContactCommand;
import edu.dosw.infrastructure.adapters.in.dtos.AddContactRequest;
import edu.dosw.infrastructure.adapters.in.dtos.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class UserWebMapperTest {

    private UserWebMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new UserWebMapper();
    }


    @Test
    void toCommand_shouldMapAddContactRequestToCommand_whenValidDataProvided() {
        // Arrange
        AddContactRequest request = new AddContactRequest();
        request.setUserId("user-123");
        request.setContactId("user-456");

        // Act
        AddContactCommand result = mapper.toCommand(request);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo("user-123");
        assertThat(result.getContactId()).isEqualTo("user-456");
    }

    @Test
    void toCommand_shouldHandleNullFields_whenRequestHasNulls() {
        // Arrange
        AddContactRequest request = new AddContactRequest();
        request.setUserId(null);
        request.setContactId(null);

        // Act
        AddContactCommand result = mapper.toCommand(request);

        // Assert
        assertThat(result.getUserId()).isNull();
        assertThat(result.getContactId()).isNull();
    }

}
