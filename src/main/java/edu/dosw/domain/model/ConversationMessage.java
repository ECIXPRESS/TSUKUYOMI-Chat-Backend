package edu.dosw.domain.model;

import lombok.AllArgsConstructor;

import java.util.Date;


public abstract class ConversationMessage {
    private String id;
    private Date sendDate;
    private Boolean isRead;
    private String text;
    private String author; //UserId
    private String conversation;

    protected ConversationMessage(String id,String conversation, Date sendDate, String text, String author){
        this.id = id;
        this.conversation = conversation;
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



}
