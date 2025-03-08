package com.example.bankapi.infrastructure.api.mapper;

import com.example.bankapi.domain.model.Transaction;
import com.example.bankapi.infrastructure.api.dto.TransactionResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransactionDtoMapper {

    public TransactionResponse toResponse(Transaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .sourceAccountId(transaction.getSourceAccountId())
                .targetAccountId(transaction.getTargetAccountId())
                .amount(transaction.getAmount())
                .timestamp(transaction.getTimestamp())
                .type(transaction.getType() != null ? transaction.getType().name() : null)
                .status(transaction.getStatus() != null ? transaction.getStatus().name() : null)
                .build();
    }

    public List<TransactionResponse> toResponseList(List<Transaction> transactions) {
        return transactions.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}