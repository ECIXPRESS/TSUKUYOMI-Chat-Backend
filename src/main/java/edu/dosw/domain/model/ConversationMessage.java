package edu.dosw.domain.model;

import java.time.Instant;
import java.util.UUID;


public abstract class ConversationMessage {
    private String id;
    private Instant sendDate;
    private Boolean isRead;
    private String text;
    private String author; //UserId
    private String conversationId;

    protected ConversationMessage(String conversationId, String text, String author){
        id = UUID.randomUUID().toString();
        this.conversationId = conversationId;
        sendDate = Instant.now();
        this.text = text;
        this.author = author;
        isRead = false;
    }

    public void markRead(){
        isRead = true;
    }

    public String getAuthor(){
        return author;
    }


    public String getConversationId() {
        return conversationId;
    }

    public String getText() {
        return text;
    }

    public String getId() {
        return id;
    }

    public Instant getSendDate() {
        return sendDate;
    }

    public boolean getIsRead() {
        return isRead;
    }

}
