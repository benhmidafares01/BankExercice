package com.example.bankapi.infrastructure.api.mapper;

import com.example.bankapi.domain.model.Account;
import com.example.bankapi.infrastructure.api.dto.AccountResponse;
import com.example.bankapi.infrastructure.api.dto.BalanceResponse;
import org.springframework.stereotype.Component;

@Component
public class AccountDtoMapper {

    public AccountResponse toResponse(Account account) {
        return AccountResponse.builder()
                .id(account.getId())
                .customerId(account.getCustomerId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .createdAt(account.getCreatedAt())
                .build();
    }

    public BalanceResponse toBalanceResponse(Account account) {
        return BalanceResponse.builder()
                .accountId(account.getId())
                .balance(account.getBalance())
                .build();
    }
}