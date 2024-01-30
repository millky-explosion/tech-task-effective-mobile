package com.example.socialmediaapi.controllers;

import com.example.socialmediaapi.dto.PostDto;
import com.example.socialmediaapi.mappers.PostMapper;
import com.example.socialmediaapi.models.PostEntity;
import com.example.socialmediaapi.security.CustomPrincipal;
import com.example.socialmediaapi.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.security.core.Authentication;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@Tag(name = "PostRestController", description = "Контроллер для работы с постами")
public class PostRestController {
    private final PostService postService;
    private final PostMapper postMapper;

    @PostMapping("/create")
    @Operation(summary = "Создание нового Поста / Записи ")
    public Mono<PostDto> createPost(@RequestBody PostDto dto, Authentication authentication) {
        PostEntity entity = postMapper.map(dto);
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();

        return postService.createPost(entity, customPrincipal.getName())
                .map(postMapper::map);
    }

    @Operation(summary = "Получение существующей Записи / Поста",
    description = "Получение поста происходит по его индивидумальному {id}")
    @GetMapping("/{id}")
    public Mono<PostDto> findById (@PathVariable(value = "id") Long id) {
        return postService.findById(id).map(postMapper::map);
    }

    @Operation(summary = "Редактирование по {id} существующей Записи / Поста ",
    description = "Редактирование существующео поста происходит по его {id}." +
            " Пользователь имеет право редактировать только те посты, которые ему принадлежат." +
            " В случае если пользователь попытается отредактировать пост, который ему не принадлежит," +
            " Будет ошибка.")
    @PostMapping("/{id}/update")
    public Mono<Void> updatePost (@RequestBody PostDto dto,
                                  @PathVariable(value = "id") Long id) {
        PostEntity entity = postMapper.map(dto);
        return postService.updatePost(dto.getText(), dto.getTitle(), id);
    }
    @Operation(summary = "Удаление по /{id} существующей Записи / Поста ",
    responses = { @ApiResponse (
            description = "Записи с данным ID не существует, либо она не принадлежит пользователю",
            responseCode = "500"
    )})
    @PostMapping("/{id}/delete")
    @ApiResponse(description = "Запись успешно удалена", responseCode = "200")
    public Mono<Void> deletePost (@PathVariable(value = "id") Long id) {
       return postService.deletePost(id);
    }
    @Operation(summary = "Последние созданные посты от Пользователей ")
    @GetMapping("/news")
    public Flux<PostEntity> getPostInfo(Authentication authentication) {
        return postService.findAll();
    }
}
