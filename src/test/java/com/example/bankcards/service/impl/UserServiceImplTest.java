package com.example.bankcards.service.impl;

import com.example.bankcards.dto.RegistrationRequest;
import com.example.bankcards.dto.UserProfileDTO;
import com.example.bankcards.entity.Role;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.rest.ConflictException;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.AccountRepository;
import com.example.bankcards.repository.RoleRepository;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.CurrentUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private CurrentUserService currentUserService;
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_Success() {
        RegistrationRequest request = new RegistrationRequest("test@test.com", "pass123", "Ivan", "Ivanov");
        Role role = mock(Role.class);

        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(anyString())).thenReturn("hashed");
        
        User savedUser = mock(User.class);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(userMapper.toProfileDto(any(User.class))).thenReturn(new UserProfileDTO("test@test.com", "Ivan", "Ivanov"));

        UserProfileDTO result = userService.createUser(request);

        assertNotNull(result);
        assertEquals("test@test.com", result.login());
        verify(accountRepository).save(any());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void createUser_Fail_AlreadyExists() {
        RegistrationRequest request = new RegistrationRequest("exists@test.com", "pass123", "Ivan", "Ivanov");
        when(userRepository.existsByEmail("exists@test.com")).thenReturn(true);

        assertThrows(ConflictException.class, () -> userService.createUser(request));
    }
}
