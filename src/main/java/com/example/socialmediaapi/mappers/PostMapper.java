package com.example.socialmediaapi.mappers;

import com.example.socialmediaapi.dto.PostDto;
import com.example.socialmediaapi.dto.UserDto;
import com.example.socialmediaapi.models.PostEntity;
import com.example.socialmediaapi.models.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto map (PostEntity post);
    @InheritInverseConfiguration
    PostEntity map(PostDto postDto);
}
