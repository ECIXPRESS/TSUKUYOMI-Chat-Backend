package edu.dosw.application;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.SendMessageUseCase;
import edu.dosw.domain.ports.outbound.ConversationRepository;
import edu.dosw.domain.ports.outbound.UserRepository;

public class SendmessageHandler implements SendMessageUseCase {

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;

    public SendmessageHandler(UserRepository userRepository, ConversationRepository conversationRepository) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
    }

    @Override
    public void execute(ConversationMessage message) {
        Conversation conversation = conversationRepository.findConversationById(message.getConversationId());
        if(conversation != null && conversation.getUsers().stream().filter(u -> u.getId().equals(message.getAuthor())).findFirst().orElse(null) != null){
            conversation.userSendMessage(message);
            User sender = conversation.getUsers().stream().filter(u -> u.getId().equals(message.getAuthor())).findFirst().orElse(null);
            conversationRepository.saveConversation(conversation);
            userRepository.saveUser(sender);
        }else {
            //exception
        }

    }
}
