package edu.dosw.application;

import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.ports.inbound.FilterMessagesUseCase;
import edu.dosw.domain.ports.outbound.ConversationRepository;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FilterMessagesUseCaseImpl implements FilterMessagesUseCase {

    private final ConversationRepository conversationRepository;

    public FilterMessagesUseCaseImpl(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    @Override
    public List<ConversationMessage> execute(String conversationId, String filterWord) {
        return conversationRepository.findConversationById(conversationId)
                .getMessages()
                .stream()
                .filter(c -> c.getText().contains(filterWord))
                .collect(Collectors.toList());
    }
}
