package edu.dosw.domain.model;

import org.apache.logging.log4j.message.Message;

import java.util.Date;
import java.util.List;

public class Conversation {
    private String id;
    private Date creationDate;
    private List<ConversationMessage> messages;
    private List<String> usersIds;

    public void addUser(String userId){
        usersIds.add(userId);
    }
    public void addMessage(ConversationMessage message){
        if(usersIds.contains(message.getAuthor())){
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
}
