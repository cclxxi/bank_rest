package com.example.bankcards.controller;

import com.example.bankcards.dto.AdminUserSummaryDTO;
import com.example.bankcards.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin Users", description = "Управление пользователями (только для администратора)")
@SecurityRequirement(name = "bearerAuth")
public class AdminUserController {

    private final UserService userService;

    @Operation(summary = "Список всех пользователей", description = "Возвращает список всех зарегистрированных пользователей с пагинацией")
    @GetMapping
    public Page<AdminUserSummaryDTO> users(@Parameter(description = "Параметры пагинации") Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @Operation(summary = "Заблокировать пользователя", description = "Устанавливает статус пользователя как заблокированный")
    @PatchMapping("/{userId}/block")
    public void block(@PathVariable Long userId) {
        userService.blockUser(userId);
    }

    @Operation(summary = "Активировать пользователя", description = "Восстанавливает активный статус пользователя")
    @PatchMapping("/{userId}/activate")
    public void activate(@PathVariable Long userId) {
        userService.activateUser(userId);
    }
}
