package com.example.bankcards.controller;

import com.example.bankcards.dto.AdminUserSummaryDTO;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserService userService;

    @GetMapping
    public Page<AdminUserSummaryDTO> users(Pageable pageable) {
        return userService.getAllUsers(pageable);
    }

    @PatchMapping("/{userId}/block")
    public void block(@PathVariable Long userId) {
        userService.blockUser(userId);
    }

    @PatchMapping("/{userId}/activate")
    public void activate(@PathVariable Long userId) {
        userService.activateUser(userId);
    }
}
