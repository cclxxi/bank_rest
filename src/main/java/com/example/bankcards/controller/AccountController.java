package com.example.bankcards.controller;

import com.example.bankcards.dto.AccountDTO;
import com.example.bankcards.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/accounts")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/balance")
    public AccountDTO balance() {
        return accountService.getUserAccount();
    }
}
