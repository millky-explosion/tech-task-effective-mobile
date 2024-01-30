package com.example.socialmediaapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.extensions.Extensions;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Тестовое Задание от Effective Mobile",
        description = "Дедлайн сдачи был с 19 августа по 25 включительно, " +
                "Мой проифль на hh.ru: https://clck.ru/35SPW6 ", version = "1.0",
        contact = @Contact(name = "Telegram: ", url = "https://t.me/herekcje")))
public class SocialMediaApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(SocialMediaApiApplication.class, args);
    }

}
