package edu.dosw.infrastructure.security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {

    private String jwtSecret;

    public TokenService(@Value("${jwt.secret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;  // ← Spring inyecta el valor aquí
    }

    public String getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)  // Valida automáticamente
                    .getBody();

            String userId = claims.get("userId", String.class);

            log.debug("Token válido - userId: {}", userId);

            return userId ;

        } catch (Exception e) {
            log.warn("Token inválido: {}", e.getMessage());
            return null;
        }
    }
}