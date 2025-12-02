package edu.dosw.application.exceptions;

public class ApplicationLayerExceptions extends RuntimeException {
    private static final String CONVERSATION_NOT_FOUND = "Conversation not found";
    private static final String USER_NOT_AUTHORIZED = "User not authorized";
    private static final String NEED_MORE_USERS = "There's needed 2 users at least to make a conversation";
    private static final String USER_OR_CONTACT_DOESNT_EXIST = "Both users must exist to add as contacts";
    private static final String USER_NOT_FOUND = "Conversation not found";

    public ApplicationLayerExceptions(String message) {
        super(message);
    }


    public static ApplicationLayerExceptions conversationNotFound() {
        return new ApplicationLayerExceptions(CONVERSATION_NOT_FOUND);
    }

    public static ApplicationLayerExceptions conversationNotFound(String conversationId) {
        return new ApplicationLayerExceptions(CONVERSATION_NOT_FOUND + ": " + conversationId);
    }

    public static ApplicationLayerExceptions userNotAuthorized() {
        return new ApplicationLayerExceptions(USER_NOT_AUTHORIZED);
    }

    public static ApplicationLayerExceptions notEnoughUsers() {
        return new ApplicationLayerExceptions(NEED_MORE_USERS);
    }

    public static ApplicationLayerExceptions cannotAddContact() {
        return new ApplicationLayerExceptions(USER_OR_CONTACT_DOESNT_EXIST);
    }

    public static ApplicationLayerExceptions userNotFound() {
        return new ApplicationLayerExceptions(USER_NOT_FOUND);
    }

}