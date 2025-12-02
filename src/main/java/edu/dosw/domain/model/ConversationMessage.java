package edu.dosw.domain.model;

import edu.dosw.domain.model.exceptions.ModelLayerExceptions;

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
        if(text.isEmpty()) throw ModelLayerExceptions.emptyMessage();

        id = UUID.randomUUID().toString();
        this.conversationId = conversationId;
        sendDate = Instant.now();
        this.text = text;
        this.author = author;
        isRead = false;
    }
    protected ConversationMessage(String id, String conversationId, String author, String text, Instant sendDate, Boolean isRead) {
        if(text.isEmpty()) throw ModelLayerExceptions.emptyMessage();

        this.id = id;
        this.conversationId = conversationId;
        this.author = author;
        this.text = text;
        this.sendDate = sendDate;
        this.isRead = isRead;
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

    public Boolean getIsRead() {
        return isRead;
    }

}
