package edu.dosw.domain.ports.inbound;

public interface CreateConversation {
    void execute(String userId,String secondUserId);
}
