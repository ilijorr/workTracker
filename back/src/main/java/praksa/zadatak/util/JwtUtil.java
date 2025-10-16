package praksa.zadatak.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.WeakKeyException;
import lombok.Getter;
import org.springframework.stereotype.Component;
import praksa.zadatak.config.JwtConfig;
import io.jsonwebtoken.security.Keys;
import praksa.zadatak.model.BaseUser;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private final Key key;

    @Getter
    private final Long expiration;

    public JwtUtil(JwtConfig config) {
         try {
            this.key = Keys.hmacShaKeyFor(
                    config.getSecret().getBytes(StandardCharsets.UTF_8)
            );
        } catch (WeakKeyException e) {
            throw new IllegalArgumentException("JWT secret must be at least 32 characters long");
        }
        this.expiration = config.getExpiration();
    }

    public String generateJwt(BaseUser user) {
        return Jwts.builder()
                .subject(user.getId().toString())
                .expiration(this.generateExpirationDate())
                .issuedAt(new Date())
                .claim("roles", extractRolesFromUser(user))
                .signWith(this.key)
                .compact();
    }

    private List<String> extractRolesFromUser(BaseUser user) {
        return user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList());
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + this.expiration);
    }
}
