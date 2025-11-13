package edu.dosw.domain.ports;

import org.apache.logging.log4j.message.Message;

public interface MarkMessageAsRead {
    void execute(Message message);
}
