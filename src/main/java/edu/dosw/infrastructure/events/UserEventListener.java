package edu.dosw.infrastructure.events;

import edu.dosw.domain.ports.inbound.SyncUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventListener {

    private final SyncUserUseCase syncUserUseCase;

    @KafkaListener(
            topics = "${kafka.topic.user-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void handleUserCreated(@Payload UserCreatedEvent event,
                                  Acknowledgment acknowledgment) {
        log.info("Evento recibido: {}", event.getUserId());

        try {
            syncUserUseCase.execute(event.getUserId(),event.getUserName());
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Error procesando evento", e);
            throw e;
        }
    }
}
