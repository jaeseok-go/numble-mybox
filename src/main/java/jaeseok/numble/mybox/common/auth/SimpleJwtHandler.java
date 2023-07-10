package jaeseok.numble.mybox.common.auth;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class SimpleJwtHandler implements JwtHandler{

    private final HttpServletRequest request;

    @Value("${jwt-token.issue-key}")
    String issueKey;

    @Value("${jwt-token.valid-time}")
    Integer validTime;

    @Override
    public String create(Long id) {
        Date current = new Date();
        Date expired = new Date(current.getTime() + validTime);
        Key key = Keys.hmacShaKeyFor(issueKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512)
                .claim("id", String.valueOf(id))
                .setIssuedAt(current)
                .setExpiration(expired)
                .compact();
    }

    @Override
    public Long getId(String jwt) {
        if (jwt == null || jwt.isBlank()) {
            throw new MyBoxException(ResponseCode.INVALID_TOKEN);
        }

        String idString = Jwts.parserBuilder()
                .setSigningKey(Base64.getEncoder().encodeToString(issueKey.getBytes()))
                .build()
                .parseClaimsJws(jwt)
                .getBody().get("id").toString();

        return Long.parseLong(idString);
    }

    @Override
    public Long getId() {
        return getId(request.getHeader("Authorization"));
    }

    @Override
    public Boolean validate(String jwt) {
        try {
            return getId(jwt) instanceof Number;
        } catch (Exception e) {
            return false;
        }
    }
}
