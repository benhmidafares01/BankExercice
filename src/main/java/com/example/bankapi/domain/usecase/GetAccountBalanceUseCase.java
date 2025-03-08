package com.example.bankapi.domain.usecase;

import java.math.BigDecimal;
import java.util.UUID;

public interface GetAccountBalanceUseCase {
    BigDecimal getBalance(UUID accountId);
}
