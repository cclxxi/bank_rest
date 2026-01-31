package com.example.bankcards.service;

import com.example.bankcards.dto.AdminUserDTO;
import com.example.bankcards.dto.AdminUserSummaryDTO;
import com.example.bankcards.dto.UserProfileDTO;

import java.util.List;

public interface UserService {

    UserProfileDTO getCurrentUserProfile();

    AdminUserDTO getUserById(Long userId);

    List<AdminUserSummaryDTO> getAllUsers();

    void activateUser(Long userId);

    void blockUser(Long userId);
}
