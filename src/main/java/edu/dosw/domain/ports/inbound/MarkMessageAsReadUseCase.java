package edu.dosw.domain.ports.inbound;


public interface MarkMessageAsReadUseCase {
    void execute(String messageId);
}
