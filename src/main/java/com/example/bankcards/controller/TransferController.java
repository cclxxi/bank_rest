package com.example.bankcards.controller;

import com.example.bankcards.dto.UserTransactionRequest;
import com.example.bankcards.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User Transfers", description = "Переводы денежных средств")
@SecurityRequirement(name = "bearerAuth")
public class TransferController {

    private final TransferService transferService;

    @Operation(summary = "Перевод между счетами", description = "Выполняет перевод средств с текущего счета пользователя на указанный номер счета")
    @ApiResponse(responseCode = "200", description = "Перевод успешно выполнен")
    @ApiResponse(responseCode = "400", description = "Недостаточно средств или некорректная сумма")
    @ApiResponse(responseCode = "404", description = "Счет получателя не найден")
    @ApiResponse(responseCode = "409", description = "Ошибка идемпотентности (транзакция с таким reference уже существует)")
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
