package praksa.zadatak.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.WeakKeyException;
import lombok.Getter;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import praksa.zadatak.config.JwtConfig;
import io.jsonwebtoken.security.Keys;
import praksa.zadatak.exception.InvalidRequestException;
import praksa.zadatak.model.BaseUser;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {
    private final SecretKey key;

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
                .claim("role", user.getRole())
                .signWith(this.key)
                .compact();
    }

    public Claims extractClaimsAndValidateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (IllegalArgumentException e) {
            throw new AuthenticationCredentialsNotFoundException("Can't validate empty JWT");
        } catch (JwtException e) {
            throw new BadCredentialsException("Invalid or expired token");
        }
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + this.expiration);
    }
}
