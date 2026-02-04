package com.example.bankcards.controller;

import com.example.bankcards.dto.UserTransactionRequest;
import com.example.bankcards.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/user/transfers")
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public void transfer(
            @Valid @RequestBody UserTransactionRequest request
    ) {
        transferService.transferMoney(
                request.toAccountNumber(),
                request.amount(),
                UUID.randomUUID().toString()
        );
    }
}
