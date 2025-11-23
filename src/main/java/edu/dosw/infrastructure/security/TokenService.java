package edu.dosw.infrastructure.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private final RestTemplate restTemplate;

    @Value("${api-gateway.url:http://localhost:8080}")
    private String apiGatewayUrl;

    /**
     * Extrae el userId del token llamando a Auth Service
     */
    public String getUserIdFromToken(String token) {
        try {
            String url = apiGatewayUrl + "/auth/extract-username?token=" + token;

            var response = restTemplate.getForEntity(url, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return (String) response.getBody().get("email");
            }

        } catch (Exception e) {
            log.error("Error extrayendo userId del token: {}", e.getMessage());
        }

        return null;
    }

    /**
     * Valida si el token es válido
     */
    public boolean isTokenValid(String token) {
        try {
            String url = apiGatewayUrl + "/auth/validate?token=" + token;

            var response = restTemplate.getForEntity(url, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return (Boolean) response.getBody().getOrDefault("valid", false);
            }

        } catch (Exception e) {
            log.error("Error validando token: {}", e.getMessage());
        }

        return false;
    }
}