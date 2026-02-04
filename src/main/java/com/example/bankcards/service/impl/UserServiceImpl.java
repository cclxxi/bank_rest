package com.example.bankcards.service.impl;

import com.example.bankcards.dto.AdminUserDTO;
import com.example.bankcards.dto.AdminUserSummaryDTO;
import com.example.bankcards.dto.RegistrationRequest;
import com.example.bankcards.dto.UserProfileDTO;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.rest.ConflictException;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.CurrentUserService;
import com.example.bankcards.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CurrentUserService currentUserService;

    @Override
    public UserProfileDTO getCurrentUserProfile() {
        User user = findUser(currentUserService.getCurrentUserId());
        return userMapper.toProfileDto(user);
    }

    @Override
    public AdminUserDTO getUserById(Long userId) {
        User user = findUser(userId);
        return userMapper.toAdminDto(user);
    }

    @Override
    public Page<AdminUserSummaryDTO> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toAdminSummaryDto);
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

    @Override
    @Transactional
    public UserProfileDTO createUser(RegistrationRequest request) {

        if (userRepository.existsByEmail(request.login())) {
            throw new ConflictException("User with this email already exists");
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Default role USER not found"));

        User user = User.create(
                request.login(),
                request.name(),
                request.surname(),
                passwordEncoder.encode(request.password()),
                userRole
        );

        user.activate();

        User savedUser = userRepository.save(user);

        return userMapper.toProfileDto(savedUser);
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
