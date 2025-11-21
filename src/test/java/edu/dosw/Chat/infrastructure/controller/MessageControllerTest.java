package edu.dosw.Chat.infrastructure.controller;

import edu.dosw.infrastructure.adapters.in.ConversationController;
import edu.dosw.infrastructure.adapters.in.dtos.ConversationMessageRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MessageControllerTest {

    @Autowired
    private ConversationController controller;

    @Test
    void testSendMessageReal() {

        ConversationMessageRequest request = new ConversationMessageRequest();
        request.setConversationId("123");
        request.setText("Hola desde test real!");

        controller.sendMessage(request);
    }
}