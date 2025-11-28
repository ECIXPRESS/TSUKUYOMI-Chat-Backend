package edu.dosw.application;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.ports.inbound.GetConversationsUseCase;
import edu.dosw.domain.ports.outbound.ConversationRepository;
import edu.dosw.domain.ports.outbound.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetConversationsUseCaseImpl implements GetConversationsUseCase {

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;

    public GetConversationsUseCaseImpl(UserRepository userRepository, ConversationRepository conversationRepository) {
        this.userRepository = userRepository;
        this.conversationRepository = conversationRepository;
    }

    @Override
    public List<Conversation> execute(String userId) {
        return userRepository.findUserById(userId).getConversations().stream().map(conversationRepository::findConversationById).toList();
    }
}
