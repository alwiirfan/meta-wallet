package com.enigma.metawallet.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class JwtUtils {

    private final Set<String> tokenBlacklist = new HashSet<>();

    @Value("${metawallet.app.jwtSecret}")
    private String jwtSecret;

    @Value("${metawallet.app.jwtExpirationMs}")
    private Long jwtExpiration;

    public String getEmailByToken(String token){
        try {
            log.info("Received JWT token : {}", token);

            Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return claims.getSubject();
        } catch (Exception e) {
            log.error("Error decoding JWT token : {}", e.getMessage());
            throw new RuntimeException("Unable to read email from token");
        }
    }

    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .compact();
    }

    public boolean validateJwtToken(String token){
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException e){
            log.error("Invalid JWT token {}", e.getMessage());
        }catch (ExpiredJwtException e){
            log.error("JWT token is expired {}", e.getMessage());
        }catch (UnsupportedJwtException e){
            log.error("JWT token is unsupported {}", e.getMessage());
        }catch (IllegalArgumentException e){
            log.error("JWT claims string is empty {}", e.getMessage());
        }
        return false;
    }

    public void addToBlacklist(String token) {
        tokenBlacklist.add(token);
    }

    public boolean isTokenBlacklisted(String token) {
        return tokenBlacklist.contains(token);
    }

}
