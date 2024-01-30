package com.example.socialmediaapi.models;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import reactor.core.publisher.MonoSink;

import java.time.LocalDateTime;
import java.util.function.Consumer;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "post")
public class PostEntity {
    @Id
    private Long id;
    private String category;
    private String title;
    private String text;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PostConstruct
    public void init ()
    {
        System.out.println("PostEntity IN INITIALIZATION");
    }

    @PreDestroy
    public void destroy ()
    {
        System.out.println("PostEntity is DESTROY");
    }
}
