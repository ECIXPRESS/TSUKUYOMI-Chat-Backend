package edu.dosw.infrastructure.persistence.mongodb.exceptions;

public class MongoPersistenceExceptions extends RuntimeException{

    private static final String CONVERSATION_NOT_FOUND = "Conversation not found on repository";

    public MongoPersistenceExceptions(String message) {
        super(message);
    }

    public static  MongoPersistenceExceptions conversationNotFound(){
        return new MongoPersistenceExceptions(CONVERSATION_NOT_FOUND);
    }

}
