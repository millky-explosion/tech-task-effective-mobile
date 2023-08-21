package com.example.socialmediaapi.service;

import com.example.socialmediaapi.models.PostEntity;
import com.example.socialmediaapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

// http://localhost:8080/api/v1/post/create
@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Mono <PostEntity> createPost (PostEntity post, String username) {
        return postRepository.save(
                         post.toBuilder()
                                 .author(username)
                                 .category("USER-PAGE")
                                 .createdAt(LocalDateTime.now())
                                 .updatedAt(LocalDateTime.now())
                        .build())
                .doOnSuccess(p -> {log.info("IN createPost - post: {} created", p);
                });
    }

    public Flux<PostEntity> findAll () {
        return postRepository.findAll();
    }

}
