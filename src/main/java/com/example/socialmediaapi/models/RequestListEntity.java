package com.example.socialmediaapi.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "requestlist")
public class RequestListEntity {
    @Id
    private Long id;
    private Long user_id;
    private Long friend_request_id;

}
