package com.example.socialmediaapi.security;

import com.example.socialmediaapi.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

import java.util.Base64;
import java.util.Date;

/*
*
*  class JWTHandler - В классе обрабатывается токен
*  и возвращается результат проверки
*
*/
public class JWTHandler {
    private final String secret;

    public JWTHandler(String secret) {
        this.secret = secret;
    }
    public static class Result {
        public Claims claims;
        public String token;

        public Result(Claims claims, String token) {
            this.claims = claims;
            this.token = token;
        }
    }
    public Mono<Result> scan (String token) {
        return Mono.just(verify(token))
            .onErrorResume(e -> Mono.error(new UnauthorizedException(e.getMessage())));
    }
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
              .setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
                .parseClaimsJws(token)
                .getBody();
    }
    private Result verify(String token) {
        Claims claims = getClaimsFromToken(token);
        final Date expirationDate = claims.getExpiration();


        if (expirationDate.before(new Date())) {
            throw new RuntimeException("Token expired");
        }

        return new Result(claims, token);
    }

}
