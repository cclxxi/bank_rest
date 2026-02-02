package com.example.bankcards.service;

import java.math.BigDecimal;

public interface TransferService {

    void transferMoney(Long toAccountId, BigDecimal amount, String reference);
}
