package com.example.bankapi.infrastructure.persistence.mapper;

import com.example.bankapi.domain.model.Account;
import com.example.bankapi.infrastructure.persistence.entity.AccountEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountEntity toEntity(Account domain) {
        return AccountEntity.builder()
                .id(domain.getId())
                .customerId(domain.getCustomerId())
                .accountNumber(domain.getAccountNumber())
                .balance(domain.getBalance())
                .createdAt(domain.getCreatedAt())
                .build();
    }

    public Account toDomain(AccountEntity entity) {
        return Account.builder()
                .id(entity.getId())
                .customerId(entity.getCustomerId())
                .accountNumber(entity.getAccountNumber())
                .balance(entity.getBalance())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}