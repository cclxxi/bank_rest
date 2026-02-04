package com.example.bankcards.service;

import com.example.bankcards.dto.AdminUserDTO;
import com.example.bankcards.dto.AdminUserSummaryDTO;
import com.example.bankcards.dto.RegistrationRequest;
import com.example.bankcards.dto.UserProfileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    UserProfileDTO getCurrentUserProfile();

    AdminUserDTO getUserById(Long userId);

    Page<AdminUserSummaryDTO> getAllUsers(Pageable pageable);

    void activateUser(Long userId);

    void blockUser(Long userId);

    UserProfileDTO createUser(RegistrationRequest registrationRequest);
}
