package edu.dosw.infrastructure.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.dosw.domain.ports.inbound.SyncUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserEventsListener implements MessageListener {

    private final SyncUserUseCase syncUserUseCase;
    private final ObjectMapper objectMapper;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            String messageBody = new String(message.getBody());
            String channel = new String(message.getChannel());

            log.info("Mensaje recibido en canal: {}", channel);
            log.debug("Contenido: {}", messageBody);

            Map<String, Object> completeEvent = objectMapper.readValue(
                    messageBody,
                    Map.class
            );

            String eventType = (String) completeEvent.get("eventType");
            Map<String, Object> data = (Map<String, Object>) completeEvent.get("data");

            if ("user.created".equals(eventType)) {
                handleUserCreated(data);
            } else if ("login.success".equals(eventType)) {
                handleLoginSuccess(data);
            } else {
                log.debug("Evento ignorado: {}", eventType);
            }

        } catch (Exception e) {
            log.error("Error procesando mensaje de Redis", e);
        }
    }

    private void handleUserCreated(Map<String, Object> data) {
        try {
            String userId = (String) data.get("userId");
            String name = (String) data.get("name");

            log.info("Procesando usuario creado: ({})", userId);

            syncUserUseCase.execute(userId, name);

            log.info("Usuario {} sincronizado exitosamente", userId);

        } catch (Exception e) {
            log.error("Error sincronizando usuario", e);
        }
    }

    private void handleLoginSuccess(Map<String, Object> data) {
        try {
            String userId = (String) data.get("userId");
            String name = (String) data.get("name");

            initializeChatForUser(userId, name);

        } catch (Exception e) {
            log.error("Error procesando evento de login exitoso", e);
        }
    }

    private void initializeChatForUser(String userId, String name) {
        try {
            log.info("Inicializando funcionalidades de chat para usuario: {}", userId);

            // 1. Sincronizar/crear usuario en el sistema de chat
            syncUserUseCase.execute(userId, name);

            log.info(" Funcionalidades de chat inicializadas para usuario: {}", userId);

        } catch (Exception e) {
            log.error("Error inicializando chat para usuario: {}", userId, e);
        }
    }
}