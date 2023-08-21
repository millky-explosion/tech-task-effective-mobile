package com.example.socialmediaapi.repository;

import com.example.socialmediaapi.models.PostEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PostRepository extends R2dbcRepository <PostEntity, Long> {
    Mono <PostEntity> findByAuthor (String author);

    @Query(value = "SELECT * FROM post ORDER BY id DESC")
    Flux <PostEntity> findAll();
}
