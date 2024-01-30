package com.example.socialmediaapi.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class PostDto {
    private Long id;
    private String category;
    private String title;
    private String text;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
