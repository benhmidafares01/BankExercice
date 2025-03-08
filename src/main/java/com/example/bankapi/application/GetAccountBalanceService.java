package com.example.bankapi.application;

import com.example.bankapi.domain.exception.AccountNotFoundException;
import com.example.bankapi.domain.port.AccountRepository;
import com.example.bankapi.domain.usecase.GetAccountBalanceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetAccountBalanceService implements GetAccountBalanceUseCase {

    private final AccountRepository accountRepository;

    @Override
    public BigDecimal getBalance(UUID accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"))
                .getBalance();
    }
}
