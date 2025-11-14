package edu.dosw.domain.model;

import org.apache.logging.log4j.message.Message;

import java.util.Date;
import java.util.List;

public class Conversation {
    private String id;
    private Date creationDate;
    private List<ConversationMessage> messages;
    private List<User> users;

    public Conversation(String id,Date creationDate,List<String> usersIds){
        this.id = id;
        this.creationDate = creationDate;
        this.users = users;
    }

    public void addUser(User user){
        users.add(user);
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

    public List<User> getUsers(){
        return users;
    }

    public List<String> getUsersIds() {
        return users.stream().map(user -> user.getId()).toList();
    }

    public List<ConversationMessage> getMessages() {
        return messages;
    }

    public void removeUser(String userId) {
        users.removeIf(u -> u.getId().equals(userId));
    }
    public void userSendMessage(ConversationMessage message){
            if(users.stream().filter(u -> u.getId().equals(message.getAuthor())).findFirst().orElse(null) != null){
                addMessage(message);
            }else{
                //exception
            }
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
