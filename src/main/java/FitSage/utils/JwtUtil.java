package fitsage.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fitsage.model.User;

import java.security.Key;
import java.util.*;
import java.util.function.Function;


@Component
public class JwtUtil {

    @Value("${jwt.secret:MySuperSecureJWTSecretKeyThatIsLongEnough123!}") // For dev/testing only
    private String jwtSecret;

    private final long EXPIRATION_MS = 86400000; 

    public String generateTokenFromUserType(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", List.of(user.getUserTypeEnum().toSpringRole())); // e.g., ["ROLE_ADMIN"]
        claims.put("userId", user.getId());
    
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();
    }
    

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public List<String> extractRoles(String token) {
        try {
            return extractClaim(token, claims -> (List<String>) claims.get("roles"));
        } catch (Exception e) {
            return new ArrayList<>(); // empty fallback
        }
    }

    public Long extractUserId(String token) {
        try {
            return extractClaim(token, claims -> {
                Object userId = claims.get("userId");
                if (userId instanceof Number) {
                    return ((Number) userId).longValue();
                }
                return null;
            });
        } catch (Exception e) {
            return null;
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    public boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date expiration = extractClaim(token, Claims::getExpiration);
        return expiration.before(new Date());
    }
}
