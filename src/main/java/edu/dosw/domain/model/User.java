package edu.dosw.domain.model;

import org.apache.logging.log4j.message.Message;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class User {
    private String id;
    private String name;
    private String profilePhoto;
    private Boolean isActive;
    private List<Conversation> conversations;
    private List<ConversationMessage> messages;
    private List<String> contacts;

    public User(String id, String name, String profilePhoto, Boolean isActive, List<Conversation> conversations, List<ConversationMessage> messages, List<String> contacts){
        this.id = id;
        this.name = name;
        this.profilePhoto = profilePhoto;
        this.isActive = isActive;
        this.conversations = conversations;
        this.messages = messages;
        this.contacts = contacts;
    }
    public void addMessage(ConversationMessage message){
        if(message.getAuthor().equals(id)){
            messages.add(message);
        }else{
            //exception
        }
    }
    public void addConversation(Conversation conversation){
        conversations.add(conversation);
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getProfilePhoto(){
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto){
        this.profilePhoto = profilePhoto;
    }

    public List<Conversation> getConversations(){
        return conversations;
    }

    public List<ConversationMessage> getMessages(){
        return messages;
    }

    public void removeConversation(String conversationId) {
        conversations = conversations.stream()
                .filter(id -> !id.equals(conversationId))
                .collect(Collectors.toList());
    }


    public List<String> getContacts() {
     return contacts;
    }

    public String getId() {
        return id;
    }
}
