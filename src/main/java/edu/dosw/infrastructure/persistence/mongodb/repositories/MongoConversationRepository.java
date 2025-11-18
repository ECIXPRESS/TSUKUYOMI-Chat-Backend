package edu.dosw.infrastructure.persistence.mongodb.repositories;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.outbound.ConversationMessageRepository;
import edu.dosw.domain.ports.outbound.ConversationRepository;
import edu.dosw.domain.ports.outbound.UserRepository;
import edu.dosw.infrastructure.persistence.mongodb.documents.ConversationDocument;
import edu.dosw.infrastructure.persistence.mongodb.mappers.ConversationMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
@Repository
public class MongoConversationRepository implements ConversationRepository {

    private final MongoTemplate mongoTemplate;
    private final ConversationMapper conversationMapper;
    private final java.util.function.Function<String, ConversationMessage> findMessage;

    public MongoConversationRepository(
            MongoTemplate mongoTemplate,
            ConversationMapper mapper,
            ConversationMessageRepository messageRepository
    ) {
        this.mongoTemplate = mongoTemplate;
        this.conversationMapper = mapper;
        this.findMessage = messageRepository::findMessageById;
    }

    @Override
    public void saveConversation(Conversation conversation) {
        mongoTemplate.save(conversationMapper.toDocument(conversation));
    }

    @Override
    public Conversation searchConversationByUserId(String userId) {
        Query query = new Query(
                Criteria.where("users").in(userId)
        );
        ConversationDocument doc = mongoTemplate.findOne(query, ConversationDocument.class);
        if (doc == null) {
            //exception
        }
        return conversationMapper.toDomain(doc, findMessage);
    }

    @Override
    public List<Conversation> listConversations() {
        List<ConversationDocument> documents =
                mongoTemplate.findAll(ConversationDocument.class);

        return documents.stream()
                .map(doc -> conversationMapper.toDomain(doc, findMessage))
                .toList();
    }

    @Override
    public void deleteConversation(String conversationId) {
        Query query = new Query(Criteria.where("_id").is(conversationId));
        mongoTemplate.remove(query, ConversationDocument.class);
    }

    @Override
    public Conversation findConversationById(String conversationId) {
        ConversationDocument doc = mongoTemplate.findById(conversationId, ConversationDocument.class);

        if (doc == null) return null;

        return conversationMapper.toDomain(doc,findMessage);
    }

    @Override
    public void updateConversation(Conversation conversation) {
        mongoTemplate.save(conversationMapper.toDocument(conversation));
    }
}
