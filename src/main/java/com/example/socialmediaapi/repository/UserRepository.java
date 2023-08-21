package com.example.socialmediaapi.repository;

import com.example.socialmediaapi.models.UserEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends R2dbcRepository <UserEntity, Long> {
    Mono<UserEntity> findByUsername(String username);
}
