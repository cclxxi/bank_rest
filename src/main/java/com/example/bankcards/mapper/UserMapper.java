package com.example.bankcards.mapper;

import com.example.bankcards.dto.AdminUserDTO;
import com.example.bankcards.dto.AdminUserSummaryDTO;
import com.example.bankcards.dto.UserProfileDTO;
import com.example.bankcards.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(target = "login", source = "email")
    UserProfileDTO toProfileDto(User user);

    @Mapping(target = "login", source = "email")
    @Mapping(target = "role", source = "role.name")
    @Mapping(target = "active", expression = "java(user.isActive())")
    AdminUserDTO toAdminDto(User user);

    @Mapping(target = "login", source = "email")
    @Mapping(target = "role", source = "role.name")
    @Mapping(target = "active", expression = "java(user.isActive())")
    AdminUserSummaryDTO toAdminSummaryDto(User user);
}
