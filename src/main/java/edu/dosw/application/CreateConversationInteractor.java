package edu.dosw.application;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.CreateConversation;
import edu.dosw.domain.ports.outbound.ConversationRepository;
import edu.dosw.domain.ports.outbound.UserRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

public class CreateConversationInteractor implements CreateConversation {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public CreateConversationInteractor(ConversationRepository conversationRepository, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(String userId , String contactId) {
        User firstUser = userRepository.findUserById(userId);
        User secondUser = userRepository.findUserById(contactId);
        if(firstUser == null || secondUser == null){
            //exception
        }
        Conversation conversation = new Conversation(UUID.randomUUID().toString(),
                new Date(),
                Arrays.asList(
                userId,
                contactId
        ));
        conversationRepository.saveConversation(conversation);
    }
}
