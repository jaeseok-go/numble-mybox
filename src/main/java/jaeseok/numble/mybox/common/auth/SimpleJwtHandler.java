package jaeseok.numble.mybox.common.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class SimpleJwtHandler implements JwtHandler{

    @Value("${jwt-token.issue-key}")
    String issueKey;

    @Value("${jwt-token.valid-time}")
    Integer validTime;

    @Override
    public String create(String id) {
        Date current = new Date();
        Date expired = new Date(current.getTime() + validTime);
        Key key = Keys.hmacShaKeyFor(issueKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .claim("id", id)
                .setIssuedAt(current)
                .setExpiration(expired)
                .compact();
    }

    @Override
    public String getId(String jwt) {
        return Jwts.parserBuilder()
                .setSigningKey(Base64.getEncoder().encodeToString(issueKey.getBytes()))
                .build()
                .parseClaimsJws(jwt)
                .getBody().get("id").toString();
    }

    @Override
    public Boolean validate(String jwt) {
        try {
            return !getId(jwt).isBlank();
        } catch (Exception e) {
            return false;
        }
    }
}
