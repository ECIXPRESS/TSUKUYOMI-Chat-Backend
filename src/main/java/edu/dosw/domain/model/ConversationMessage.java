package edu.dosw.domain.model;

import lombok.AllArgsConstructor;

import java.util.Date;


public abstract class ConversationMessage {
    private String id;
    private Date sendDate;
    private Boolean isRead;
    private String text;
    private String author; //UserId
    private String conversationId;

    protected ConversationMessage(String id,String conversationId, Date sendDate, String text, String author){
        this.id = id;
        this.conversationId = conversationId;
        this.sendDate = sendDate;
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

    public Date getSendDate() {
        return sendDate;
    }

    public boolean getIsRead() {
        return isRead;
    }

}
