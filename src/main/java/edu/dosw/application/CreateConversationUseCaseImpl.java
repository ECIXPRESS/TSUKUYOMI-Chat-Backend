package edu.dosw.application;

import edu.dosw.application.exceptions.ApplicationLayerExceptions;
import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.CreateConversationUseCase;
import edu.dosw.domain.ports.inbound.command.CreateConversationCommand;
import edu.dosw.domain.ports.outbound.ConversationRepository;
import edu.dosw.domain.ports.outbound.UserRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CreateConversationUseCaseImpl implements CreateConversationUseCase {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public CreateConversationUseCaseImpl(ConversationRepository conversationRepository, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Conversation execute(CreateConversationCommand command) {

        List<String> validUserIds = new ArrayList<>();

        for (String userId : command.getUsersIds()) {
            User user = userRepository.findUserById(userId);
            if (user != null) {
                validUserIds.add(user.getId());
            }
        }

        if (validUserIds.size() < 2) {
            throw ApplicationLayerExceptions.notEnoughUsers();
        }

        Conversation conversation = new Conversation(validUserIds,command.getOrderId());
        conversationRepository.saveConversation(conversation);

        for (String userId : validUserIds) {
            User user = userRepository.findUserById(userId);
            user.getConversations().add(conversation.getId());
            userRepository.updateUser(user);
        }

        return conversation;
    }
}
