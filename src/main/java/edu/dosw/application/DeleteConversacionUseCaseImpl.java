package edu.dosw.application;

import edu.dosw.application.exceptions.ApplicationLayerExceptions;
import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.DeleteConversationUseCase;
import edu.dosw.domain.ports.inbound.command.DeleteConversationCommand;
import edu.dosw.domain.ports.outbound.ConversationRepository;
import edu.dosw.domain.ports.outbound.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeleteConversacionUseCaseImpl implements DeleteConversationUseCase {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public DeleteConversacionUseCaseImpl(ConversationRepository conversationRepository, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(DeleteConversationCommand deleteConversationCommand) {
        Conversation conversation = conversationRepository.findConversationById(deleteConversationCommand.getConversationId());
        User user = userRepository.findUserById(deleteConversationCommand.getUserId());

        if(conversation == null) throw ApplicationLayerExceptions.conversationNotFound();
        if(user == null) throw  ApplicationLayerExceptions.userNotFound();

        conversation.removeUser(deleteConversationCommand.getUserId());
        user.removeConversation(deleteConversationCommand.getConversationId());

        List<String> remainingUsersIdsOfConversation = conversation.getUsersIds();

        if (remainingUsersIdsOfConversation.size() < 2){
            List<User> remainingUsers = remainingUsersIdsOfConversation.stream()
                    .map(userRepository::findUserById)
                    .collect(Collectors.toList());

            remainingUsers.forEach(u -> {
                u.removeConversation(deleteConversationCommand.getConversationId());
                userRepository.updateUser(u);
            });
            conversationRepository.deleteConversation(conversation.getId());
        }else{
            conversationRepository.updateConversation(conversation);
        }
        userRepository.updateUser(user);
    }
}
