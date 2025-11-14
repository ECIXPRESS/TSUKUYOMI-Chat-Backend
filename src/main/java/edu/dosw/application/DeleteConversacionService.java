package edu.dosw.application;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.inbound.DeleteConversationUseCase;
import edu.dosw.domain.ports.outbound.ConversationRepository;
import edu.dosw.domain.ports.outbound.UserRepository;

public class DeleteConversacionService implements DeleteConversationUseCase {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public DeleteConversacionService(ConversationRepository conversationRepository, UserRepository userRepository) {
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void execute(String conversationId, String userId) {
        Conversation conversation = conversationRepository.searchConversationByUserId(conversationId);
        User user = userRepository.findUserById(userId);
        if(conversation != null && user != null){
            conversation.removeUser(userId);
            user.removeConversation(conversationId);
        }else{
            //exception
        }
    }
}
