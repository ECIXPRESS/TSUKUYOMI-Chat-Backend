package edu.dosw.domain.model;

import java.util.Date;

public class Regular extends ConversationMessage{
    public Regular(String id, String conversation, Date sendDate, String text, String author) {
        super(id, conversation, sendDate, text, author);
    }
}
