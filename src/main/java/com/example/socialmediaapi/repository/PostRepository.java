package com.example.socialmediaapi.repository;

import com.example.socialmediaapi.models.PostEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public interface PostRepository extends R2dbcRepository <PostEntity, Long> {
    Mono <PostEntity> findByAuthor (String author);
    @Query(value = "SELECT * FROM post ORDER BY id DESC")
    Flux <PostEntity> findAll();
    @Query("update post p set text =:text, title =:title, updated_at=:updated_at " +
            "where id in (:ids)")
    Mono <Void> update (String text, String title, LocalDateTime updated_at, Long ids);

}
