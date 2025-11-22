package edu.dosw.infrastructure.security;

import edu.dosw.domain.ports.inbound.SyncUserUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.Map;

@Component
public class WebSocketAuthInterceptor implements ChannelInterceptor {

    private final RestTemplate restTemplate;
    private final SyncUserUseCase syncUserUseCase;

    @Value("${api.gateway.url:http://localhost:8081}")
    private String apiGatewayUrl;

    public WebSocketAuthInterceptor(RestTemplate restTemplate, SyncUserUseCase syncUserUseCase) {
        this.restTemplate = restTemplate;
        this.syncUserUseCase = syncUserUseCase;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);

                String userId = extractUserIdFromToken(token);

                if (userId != null) {
                    // Sincronizar usuario usando solo el userId
                    syncUserUseCase.execute(userId, "User-" + userId.substring(0, 8)); // Nombre temporal

                    // Crear Principal con el userId
                    Principal principal = () -> userId;
                    accessor.setUser(principal);

                    System.out.println("User authenticated: " + userId);
                } else {
                    throw new IllegalArgumentException("Invalid token");
                }
            } else {
                throw new IllegalArgumentException("Authentication required");
            }
        }

        return message;
    }

    private String extractUserIdFromToken(String token) {
        try {
            String url = apiGatewayUrl + "/api/auth/me";

            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.set("Authorization", "Bearer " + token);

            org.springframework.http.HttpEntity<String> entity = new org.springframework.http.HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(url,
                    org.springframework.http.HttpMethod.GET, entity, Map.class);

            if (response.getBody() != null) {
                return (String) response.getBody().get("userId");
            }
            return null;

        } catch (Exception e) {
            System.err.println("Error extracting userId: " + e.getMessage());
            return null;
        }
    }