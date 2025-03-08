package com.example.bankapi.domain.usecase;

import com.example.bankapi.domain.model.Transaction;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransferMoneyUseCase {
    Transaction transferMoney(UUID sourceAccountId, UUID targetAccountId, BigDecimal amount);
}
