package com.example.bankcards.service;

import com.example.bankcards.dto.TransactionDTO;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getUserTransactions();
}
