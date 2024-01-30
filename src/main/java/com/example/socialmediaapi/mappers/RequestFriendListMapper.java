package com.example.socialmediaapi.mappers;

import com.example.socialmediaapi.dto.RequestListDto;
import com.example.socialmediaapi.models.RequestListEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestFriendListMapper {

    RequestListDto map(RequestListEntity friendListEntity);
    @InheritInverseConfiguration
    RequestListEntity map(RequestListDto dto);
}
