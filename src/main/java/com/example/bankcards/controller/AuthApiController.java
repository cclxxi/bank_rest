package com.example.bankcards.controller;

import com.example.bankcards.dto.AuthResponseDTO;
import com.example.bankcards.dto.LoginRequest;
import com.example.bankcards.dto.RegistrationRequest;
import com.example.bankcards.service.impl.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Методы для регистрации и входа в систему")
public class AuthApiController {

    private final AuthService authService;

    @Operation(summary = "Вход в систему", description = "Аутентифицирует пользователя и возвращает JWT токен")
    @ApiResponse(responseCode = "200", description = "Успешный вход",
            content = @Content(schema = @Schema(implementation = AuthResponseDTO.class)))
    @ApiResponse(responseCode = "401", description = "Неверный логин или пароль")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequest request
    ) {
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Регистрация нового пользователя", description = "Создает нового пользователя с ролью USER")
    @ApiResponse(responseCode = "201", description = "Пользователь успешно зарегистрирован",
            content = @Content(schema = @Schema(implementation = AuthResponseDTO.class)))
    @ApiResponse(responseCode = "409", description = "Пользователь с таким email уже существует")
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody RegistrationRequest request
    ) {
        AuthResponseDTO response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}

