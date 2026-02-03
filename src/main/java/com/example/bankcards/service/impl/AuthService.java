package com.example.bankcards.service.impl;

import com.example.bankcards.dto.AuthResponseDTO;
import com.example.bankcards.dto.LoginRequest;
import com.example.bankcards.dto.RegistrationRequest;
import com.example.bankcards.dto.UserProfileDTO;
import com.example.bankcards.entity.User;
import com.example.bankcards.mapper.UserMapper;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.security.CustomUserDetails;
import com.example.bankcards.security.JwtUtil;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserService userService;

    @Transactional(readOnly = true)
    public AuthResponseDTO login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.login(),
                        request.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        UserProfileDTO mapped = userMapper.toProfileDto(user);

        String token = jwtUtil.generateToken((CustomUserDetails) authentication.getPrincipal());

        return new AuthResponseDTO(
                mapped,
                "Bearer " + token
        );
    }

    @Transactional
    public AuthResponseDTO register(RegistrationRequest request) {

        UserProfileDTO user = userService.createUser(request);

        // Сразу логиним пользователя
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.login(),
                        request.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtUtil.generateToken(
                (CustomUserDetails) authentication.getPrincipal()
        );

        return new AuthResponseDTO(
                user,
                "Bearer " + token
        );
    }
}
