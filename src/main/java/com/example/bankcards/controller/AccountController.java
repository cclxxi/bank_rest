package com.example.bankcards.controller;

import com.example.bankcards.dto.AccountDTO;
import com.example.bankcards.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/accounts")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
@Tag(name = "User Accounts", description = "Управление банковскими счетами пользователя")
@SecurityRequirement(name = "bearerAuth")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Получить баланс", description = "Возвращает информацию о текущем банковском счете пользователя и его баланс")
    @GetMapping("/balance")
    public AccountDTO balance() {
        return accountService.getUserAccount();
    }
}
