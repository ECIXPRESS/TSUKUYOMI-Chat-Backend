package edu.dosw.domain.ports;

public interface CreateConversation {
    void execute(String userId,String secondUserId);
}
