package edu.dosw.domain.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Conversation {
    private final String id;
    private final Instant creationDate;
    private List<ConversationMessage> messages;
    private List<String> users;
    private final String orderId;

    public Conversation(String conversationId, Instant creationDate, List<String> users, List<ConversationMessage> messages, String orderId){
        id = conversationId;
        this.creationDate = creationDate;
        this.users = users;
        this.messages = messages;
        this.orderId = orderId;
    }

    public Conversation(List<String> users, String orderId){
        this.orderId = orderId;
        id = UUID.randomUUID().toString();
        creationDate = Instant.now();
        this.users = users;
        messages = new ArrayList<>();
    }

    public void addUser(String userId){
        if(users.stream().anyMatch(s -> s.equals(userId))){
            //no se puede agregar otra vez
        }
        users.add(userId);
    }
    public void addMessage(ConversationMessage message){
        if(getUsersIds().contains(message.getAuthor())){
            messages.add(message);
        }else {
            //exception
        }
    }
    public void markMessageRead(ConversationMessage message){
        if(messages.contains(message)){
            message.markRead();
        }else {
            // exception
        }
    }
    public String getId() {
        return id;
    }

    public List<String> getUsersIds() {
        return users;
    }

    public List<ConversationMessage> getMessages() {
        return messages;
    }

    public void removeUser(String userId) {
        users.removeIf(u -> u.equals(userId));
    }
    public void userSendMessage(ConversationMessage message){
            if(users.stream().filter(u -> u.equals(message.getAuthor())).findFirst().orElse(null) != null){
                addMessage(message);
            }else{
                //exception
            }
    }

    public String getOrderId() {
        return orderId;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public List<String> getMessagesIds() {
        return messages.stream().map(ConversationMessage::getId).toList();
    }
}
