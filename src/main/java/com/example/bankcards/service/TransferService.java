package com.example.bankcards.service;

import java.math.BigDecimal;

public interface TransferService {

    void transferMoney(String toAccountNumber, BigDecimal amount, String reference);
}
