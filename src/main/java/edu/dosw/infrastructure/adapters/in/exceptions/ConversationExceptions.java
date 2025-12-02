package edu.dosw.infrastructure.adapters.in.exceptions;

public class ConversationExceptions extends RuntimeException {
    private static final String USER_FAILED_AUTH = "Autenticacion del usuario fallida";
    public ConversationExceptions(String message) {
        super(message);
    }

    public static ConversationExceptions failedAuth(){
        return new ConversationExceptions(USER_FAILED_AUTH);
    }



}
