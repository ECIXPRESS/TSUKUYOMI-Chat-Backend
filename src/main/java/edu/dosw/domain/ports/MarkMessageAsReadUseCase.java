package edu.dosw.domain.ports;

import org.apache.logging.log4j.message.Message;

public interface MarkMessageAsReadUseCase {
    void execute(Message message);
}
