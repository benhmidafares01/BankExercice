package com.example.bankapi.domain.usecase;

import com.example.bankapi.domain.model.Account;

import java.math.BigDecimal;
import java.util.UUID;

public interface CreateAccountUseCase {
    Account createAccount(UUID customerId, BigDecimal initialDeposit);
}
