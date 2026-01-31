package com.example.bankcards.service.impl;

import com.example.bankcards.dto.AdminUserDTO;
import com.example.bankcards.dto.AdminUserSummaryDTO;
import com.example.bankcards.dto.UserProfileDTO;
import com.example.bankcards.entity.User;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserProfileDTO getCurrentUserProfile() {
        User user = getCurrentUser();
        return userMapper.toProfileDto(user);
    }

    @Override
    public AdminUserDTO getUserById(Long userId) {
        User user = findUser(userId);
        return userMapper.toAdminDto(user);
    }

    @Override
    public List<AdminUserSummaryDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toAdminSummaryDto)
                .toList();
    }

    @Override
    @Transactional
    public void activateUser(Long userId) {
        findUser(userId).activate();
    }

    @Override
    @Transactional
    public void blockUser(Long userId) {
        findUser(userId).block();
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    private User getCurrentUser() {
        // через SecurityContext
        throw new UnsupportedOperationException();
    }
}
