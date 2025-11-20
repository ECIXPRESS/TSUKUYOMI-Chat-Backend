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
    private List<String> conversations;
    private List<String> contacts;


    public User(String userId,String name, String profilePhoto){
        this.name = name;
        this.profilePhoto = profilePhoto;
        isActive = false;
        conversations = new ArrayList<>();
        contacts = new ArrayList<>();
    }

    public User(String userId,String name, String profilePhoto, List<String> conversations,List<String> contacts ){
        this.id = userId;
        this.name = name;
        this.profilePhoto = profilePhoto;

    }

    public void addConversation(String conversationId){
        conversations.add(conversationId);
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

    public List<String> getConversations(){
        return conversations;
    }


    public void removeConversation(String conversationId) {
        conversations = conversations.stream()
                .filter(c -> !c.equals(conversationId))
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
