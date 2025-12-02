package edu.dosw.infrastructure.persistence.mongodb.repositories;


import edu.dosw.domain.model.ConversationMessage;
import edu.dosw.domain.ports.outbound.ConversationMessageRepository;
import edu.dosw.infrastructure.persistence.mongodb.documents.ConversationMessageDocument;
import edu.dosw.infrastructure.persistence.mongodb.mappers.ConversationMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MongoConversationMessageRepository implements ConversationMessageRepository {

    private final MongoTemplate mongoTemplate;
    private final ConversationMessageMapper mapper;

    @Autowired
    public MongoConversationMessageRepository(MongoTemplate mongoTemplate,ConversationMessageMapper mapper) {
        this.mongoTemplate = mongoTemplate;
        this.mapper = mapper;
    }

    @Override
    public void saveConversationMessage(ConversationMessage message) {
        mongoTemplate.save(mapper.toDocument(message));
    }

    @Override
    public ConversationMessage findMessageById(String messageId) {
        ConversationMessageDocument doc =mongoTemplate.findById(messageId, ConversationMessageDocument.class);
        return doc != null ? mapper.toDomain(doc) : null; //cambiar por exception
    }

    @Override
    public List<ConversationMessage> findMessagesByConversationId(String conversationId) {
        Query query = new Query(
                Criteria.where("conversation_id").is(conversationId)
        ).with(Sort.by(Sort.Direction.ASC, "send_Date"));

        List<ConversationMessageDocument> docs =
                mongoTemplate.find(query, ConversationMessageDocument.class);

        return docs.stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public List<ConversationMessage> findMessagesByUserId(String userId) {
        Query query = new Query(
                Criteria.where("author_id").is(userId)
        ).with(Sort.by(Sort.Direction.ASC, "send_date"));

        List<ConversationMessageDocument> docs =
                mongoTemplate.find(query, ConversationMessageDocument.class);

        return docs.stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public void updateConversationMessage(ConversationMessage message) {
        mongoTemplate.save(mapper.toDocument(message));
    }

    @Override
    public void deleteMessageById(String messageId) {
        Query query = new Query(Criteria.where("_id").is(messageId));
        mongoTemplate.remove(query, ConversationMessageDocument.class);
    }
}
