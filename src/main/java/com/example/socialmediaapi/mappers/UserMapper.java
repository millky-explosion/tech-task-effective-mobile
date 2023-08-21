package com.example.socialmediaapi.mappers;

import com.example.socialmediaapi.dto.UserDto;
import com.example.socialmediaapi.models.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto map(UserEntity userEntity);
    @InheritInverseConfiguration
    UserEntity map(UserDto dto);
}