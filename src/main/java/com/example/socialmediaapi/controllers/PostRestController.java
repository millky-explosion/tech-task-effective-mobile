package com.example.socialmediaapi.controllers;

import com.example.socialmediaapi.dto.PostDto;
import com.example.socialmediaapi.mappers.PostMapper;
import com.example.socialmediaapi.models.PostEntity;
import com.example.socialmediaapi.security.CustomPrincipal;
import com.example.socialmediaapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.security.core.Authentication;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostRestController {
    private final PostService postService;
    private final PostMapper postMapper;

    @PostMapping("/create")
    public Mono<PostDto> register(@RequestBody PostDto dto, Authentication authentication) {
        PostEntity entity = postMapper.map(dto);
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();

        return postService.createPost(entity, customPrincipal.getName())
                .map(postMapper::map);
    }

    @GetMapping("/news")
    public Flux<PostEntity> getUserInfo(Authentication authentication) {
        return postService.findAll();
    }
}
