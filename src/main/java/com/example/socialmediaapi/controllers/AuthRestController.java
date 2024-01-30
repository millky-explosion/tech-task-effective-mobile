package com.example.socialmediaapi.controllers;

import com.example.socialmediaapi.dto.AuthRequestDto;
import com.example.socialmediaapi.dto.AuthResponseDto;
import com.example.socialmediaapi.dto.UserDto;
import com.example.socialmediaapi.mappers.UserMapper;
import com.example.socialmediaapi.models.UserEntity;
import com.example.socialmediaapi.security.CustomPrincipal;
import com.example.socialmediaapi.security.SecurityConfiguration;
import com.example.socialmediaapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@Tag(name = "AuthRestController", description = "Контроллер для регистрации и авторизации пользователя")
public class AuthRestController {
    private final SecurityConfiguration securityService;
    private final UserService userService;
    private final UserMapper userMapper;


    @PostMapping("/register")
    @Operation(description = "Создание нового пользователя / Регистрация",
    summary = "Создание нового пользователя / Регистрация")
    public Mono<UserDto> register(@RequestBody UserDto dto) {
        UserEntity entity = userMapper.map(dto);
        return userService.registerUser(entity)
                .map(userMapper::map);
    }

    @PostMapping("/login")
    @Operation(summary = "Авторизация существующего пользователя",
            description = "В случае успшеной аутентификации, пользователю" +
                    " генерируется и выдается токен для дальнешей работы с сервисом")
    public Mono<AuthResponseDto> login(@RequestBody AuthRequestDto dto) {
        return securityService.authenticate(dto.getUsername(), dto.getPassword())
                .flatMap(tokenDetails -> Mono.just(
                        AuthResponseDto.builder()
                                .userId(tokenDetails.getUserId())
                                .token(tokenDetails.getToken())
                                .issuedAt(tokenDetails.getIssuedAt())
                                .expiresAt(tokenDetails.getExpiresAt())
                                .build()
                ));
    }

    @GetMapping("/info")
    @Operation(summary = "Личный кабинет Пользователя",
            description = "Аутентифицированые пользователи получают доступ к личной информации " +
                    " о своих данных")
    public Mono<UserDto> getUserInfo(Authentication authentication) {
        CustomPrincipal customPrincipal = (CustomPrincipal) authentication.getPrincipal();

        return userService.getUserById(customPrincipal.getId())
                .map(userMapper::map);
    }


}