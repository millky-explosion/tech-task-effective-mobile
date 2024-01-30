package com.example.socialmediaapi.security;

import com.example.socialmediaapi.exception.CustomAuthorizationException;
import com.example.socialmediaapi.models.UserEntity;
import com.example.socialmediaapi.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import java.util.*;

/*
 * class SecurityConfiguration -  Логика генерации токена
 */
@Component
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Integer expirationInSeconds;
    @Value("${jwt.issuer}")
    private String issuer;


    /* Генерация токена для пользователя  */
    private TokenDetails generateToken(Date expirationDate, Map<String, Object> claims, String subject) {
        Date createdDate = new Date();

        /* Сеттинг параметров токена для пользователя */
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(issuer)
                .setSubject(subject)
                .setIssuedAt(createdDate)
                .setId(UUID.randomUUID().toString())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secret.getBytes()))
                .compact();

        /* Билдим и возвращаем */
        return TokenDetails.builder()
                .token(token)
                .issuedAt(createdDate)
                .expiresAt(expirationDate)
                .build();
    }
    /* Генерация даты истечения срока токена  */
    private TokenDetails generateToken(Map<String, Object> claims, String subject) {
        Long expirationTimeInMillis = expirationInSeconds * 1000L;

        /* expirationDate = Текущая дата + expirationTimeInMillis (1000 м/c)  */
        Date expirationDate = new Date(new Date().getTime() + expirationTimeInMillis);

        return generateToken(expirationDate, claims, subject);
    }

    /* Метод для выдачи токена при успехе в ->  public Mono<TokenDetails> authenticate  */
     private TokenDetails generateToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>() {{
            put("role", user.getRole());
            put("username", user.getUsername());
        }};
        return generateToken(claims, user.getId().toString());
    }


    /* Поиск пользователя в БД по вводным данным */
    public Mono<TokenDetails> authenticate(String username, String password) {
        return userService.getUserByUsername(username)
                .flatMap(user -> {

                    /* Проверяем его валидность -> CustomAuthorizationException */
                    if (!user.isEnabled()) {
                        return Mono.error(new CustomAuthorizationException("Account disabled", "USER_ACCOUNT_DISABLED"));
                    }

                    /* Если вводный пароль не совпадает -> CustomAuthorizationException */
                    if (!passwordEncoder.matches(password, user.getPassword())) {
                        return Mono.error(new CustomAuthorizationException("Invalid password", "INVALID_PASSWORD"));
                    }

                    /* Если все корректно, отдаем токен пользователю   */
                    return Mono.just(generateToken(user).toBuilder()
                            .userId(user.getId())
                            .build());
                })

                    /* Если не найден */
                .switchIfEmpty(Mono.error(new CustomAuthorizationException("Invalid username", "INVALID_USERNAME")));
    }
}
