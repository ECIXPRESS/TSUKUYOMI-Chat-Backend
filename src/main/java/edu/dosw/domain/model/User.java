package edu.dosw.domain.model;

import org.apache.logging.log4j.message.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public class User {
    private String id;
    private String name;
    private String profilePhoto;
    private Boolean isActive;
    private List<Conversation> conversations;
    private List<String> contacts;

    public User( String name, String profilePhoto){
        id = UUID.randomUUID().toString();
        this.name = name;
        this.profilePhoto = profilePhoto;
        isActive = false;
        conversations = new ArrayList<>();
        contacts = new ArrayList<>();
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


    public void removeConversation(String conversationId) {
        conversations = conversations.stream()
                .filter(c -> !c.getId().equals(conversationId))
                .collect(Collectors.toList());
    }


    public List<String> getContacts() {
     return contacts;
    }

    public String getId() {
        return id;
    }

    public boolean getIsActive() {
        return isActive;
    }
}
