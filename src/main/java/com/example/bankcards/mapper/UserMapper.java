package com.example.bankcards.mapper;

import com.example.bankcards.dto.AdminUserDTO;
import com.example.bankcards.dto.AdminUserSummaryDTO;
import com.example.bankcards.dto.UserProfileDTO;
import com.example.bankcards.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserProfileDTO toProfileDto(User user);

    @Mapping(target = "role", source = "role.name")
    AdminUserDTO toAdminDto(User user);

    @Mapping(target = "role", source = "role.name")
    AdminUserSummaryDTO toAdminSummaryDto(User user);
}
