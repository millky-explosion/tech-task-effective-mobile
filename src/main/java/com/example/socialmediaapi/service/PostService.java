package com.example.socialmediaapi.service;

import com.example.socialmediaapi.models.PostEntity;
import com.example.socialmediaapi.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

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
                .doOnSuccess(p -> {log.info("Success NEW POST : {} ", p);
                });
    }
    public Mono<Void> updatePost(String text, String title, Long id) {
        return postRepository.update(text, title, LocalDateTime.now(), id)
                .doOnSuccess(u -> {
                    log.info("Success UPDATE in POST : {} ", u);
                });
    }

    public Mono<Void> deletePost (Long id) {
        return postRepository.deleteById(id).doOnSuccess(u -> {
            log.info("Success DELETE in POST : {} ", u);
        });
    }

    public Mono<PostEntity> findById (Long id) {
        return postRepository.findById(id)
                .doOnSuccess(u -> {
                    log.info("Success FIND BY ID in POST : {} ", u);
                });
    }

    public Mono<PostEntity> findByAuhtor (String author) {
        return postRepository.findByAuthor(author);
    }

    public Flux<PostEntity> findAll () {
        return postRepository.findAll();
    }

}
