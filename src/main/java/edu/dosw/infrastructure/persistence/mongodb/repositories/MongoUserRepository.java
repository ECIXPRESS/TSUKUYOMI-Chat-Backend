package edu.dosw.infrastructure.persistence.mongodb.repositories;

import edu.dosw.domain.model.Conversation;
import edu.dosw.domain.model.User;
import edu.dosw.domain.ports.outbound.ConversationRepository;
import edu.dosw.domain.ports.outbound.UserRepository;
import edu.dosw.infrastructure.persistence.mongodb.documents.UserDocument;
import edu.dosw.infrastructure.persistence.mongodb.mappers.UserMapper;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class MongoUserRepository implements UserRepository {
    private final MongoTemplate mongoTemplate;
    private final UserMapper userMapper;
    private final java.util.function.Function<String, Conversation> findConversation;


    public MongoUserRepository(MongoTemplate mongoTemplate, UserMapper userMapper, ConversationRepository conversationRepository) {
        this.mongoTemplate = mongoTemplate;
        this.userMapper = userMapper;
        this.findConversation = conversationRepository ::findConversationById;
    }

    @Override
    public void saveUser(User user) {
        mongoTemplate.save(userMapper.toDocument(user));
    }

    @Override
    public List<User> listUsers() {
        return mongoTemplate.findAll(UserDocument.class).stream()
                .map(userDocument -> userMapper.toDomain(userDocument, findConversation))
                .toList();
    }

    @Override
    public void deleteUser(String userId) {
        Query query = new Query(Criteria.where("_id").is(userId));
        mongoTemplate.remove(query, UserDocument.class);
    }

    @Override
    public User findUserById(String userId) {
        UserDocument doc = mongoTemplate.findById(userId, UserDocument.class);
        if (doc == null) return null;
        return userMapper.toDomain(doc, findConversation);
    }

    @Override
    public void updateUser(User user) {
        mongoTemplate.save(userMapper.toDocument(user));
    }
}
