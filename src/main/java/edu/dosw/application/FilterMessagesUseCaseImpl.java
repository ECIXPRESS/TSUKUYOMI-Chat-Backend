package edu.dosw.application;

import edu.dosw.application.exceptions.ApplicationLayerExceptions;
import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.ports.inbound.FilterMessagesUseCase;
import edu.dosw.domain.ports.outbound.ConversationRepository;
import edu.dosw.infrastructure.persistence.mongodb.exceptions.MongoPersistenceExceptions;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FilterMessagesUseCaseImpl implements FilterMessagesUseCase {

    private final ConversationRepository conversationRepository;

    public FilterMessagesUseCaseImpl(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Override
    public List<ConversationMessage> execute(String conversationId, String filterWord) {
        try {
            return conversationRepository.findConversationById(conversationId)
                    .getMessages()
                    .stream()
                    .filter(c -> c.getText().contains(filterWord))
                    .toList();

        } catch (MongoPersistenceExceptions e) {
            throw ApplicationLayerExceptions.conversationNotFound();
        }
    }
}
