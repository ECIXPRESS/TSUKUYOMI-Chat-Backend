package edu.dosw.application;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.CreateConversationUseCase;
import edu.dosw.domain.ports.inbound.command.CreateConversationCommand;
import edu.dosw.domain.ports.outbound.ConversationRepository;
import edu.dosw.domain.ports.outbound.UserRepository;

import java.util.*;

public class CreateConversationUseCaseImpl implements CreateConversationUseCase {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public CreateConversationUseCaseImpl(ConversationRepository conversationRepository, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Conversation execute(CreateConversationCommand command) {
        Conversation conversation = new Conversation(
                command.getUsersIds().stream()
                        .map(userRepository::findUserById)
                        .filter(Objects::nonNull).toList()
                        .stream()
                        .map(User::getId)
                        .toList());
        conversationRepository.saveConversation(conversation);
        return conversation;
    }
}
