package edu.dosw.application.applicationmappers;

import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.model.Regular;
import edu.dosw.domain.ports.inbound.command.SendConversationMessageCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConversationMessageApplicationMapperTest {

    private ConversationMessageApplicationMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ConversationMessageApplicationMapper();
    }

    @Test
    void toDomain_shouldMapCorrectly_whenCommandIsValid() {
        // Arrange
        SendConversationMessageCommand command = new SendConversationMessageCommand(
                "conv-123",
                "user-1",
                "Hola, ¿cómo estás?"
        );

        // Act
        ConversationMessage result = mapper.toDomain(command);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isInstanceOf(Regular.class);
        assertThat(result.getConversationId()).isEqualTo("conv-123");
        assertThat(result.getAuthor()).isEqualTo("user-1");
        assertThat(result.getText()).isEqualTo("Hola, ¿cómo estás?");
        assertThat(result.getId()).isNotNull();
        assertThat(result.getSendDate()).isNotNull();
        assertThat(result.getIsRead()).isFalse();
    }

    @Test
    void toDomain_shouldGenerateUniqueIds_whenCalledMultipleTimes() {
        // Arrange
        SendConversationMessageCommand command1 = new SendConversationMessageCommand(
                "conv-123",
                "user-1",
                "Mensaje 1"
        );
        SendConversationMessageCommand command2 = new SendConversationMessageCommand(
                "conv-123",
                "user-1",
                "Mensaje 2"
        );

        // Act
        ConversationMessage result1 = mapper.toDomain(command1);
        ConversationMessage result2 = mapper.toDomain(command2);

        // Assert
        assertThat(result1.getId()).isNotEqualTo(result2.getId());
    }

    @Test
    void toDomain_shouldPreserveAllFields_whenMappingCommand() {
        // Arrange
        SendConversationMessageCommand command = new SendConversationMessageCommand(
                "conv-999",
                "user-999",
                "Texto de prueba"
        );

        // Act
        ConversationMessage result = mapper.toDomain(command);

        // Assert
        assertThat(result.getConversationId()).isEqualTo(command.getConversationId());
        assertThat(result.getAuthor()).isEqualTo(command.getAuthorId());
        assertThat(result.getText()).isEqualTo(command.getText());
    }
}
