package com.example.bankapi.infrastructure.persistence.mapper;

import com.example.bankapi.domain.model.Transaction;
import com.example.bankapi.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionEntity toEntity(Transaction domain) {
        return TransactionEntity.builder()
                .id(domain.getId())
                .sourceAccountId(domain.getSourceAccountId())
                .targetAccountId(domain.getTargetAccountId())
                .amount(domain.getAmount())
                .timestamp(domain.getTimestamp())
                .type(mapTransactionType(domain.getType()))
                .status(mapTransactionStatus(domain.getStatus()))
                .build();
    }

    public Transaction toDomain(TransactionEntity entity) {
        return Transaction.builder()
                .id(entity.getId())
                .sourceAccountId(entity.getSourceAccountId())
                .targetAccountId(entity.getTargetAccountId())
                .amount(entity.getAmount())
                .timestamp(entity.getTimestamp())
                .type(mapTransactionType(entity.getType()))
                .status(mapTransactionStatus(entity.getStatus()))
                .build();
    }

    private TransactionEntity.TransactionType mapTransactionType(Transaction.TransactionType type) {
        if (type == null) return null;
        switch (type) {
            case DEPOSIT: return TransactionEntity.TransactionType.DEPOSIT;
            case WITHDRAWAL: return TransactionEntity.TransactionType.WITHDRAWAL;
            case TRANSFER: return TransactionEntity.TransactionType.TRANSFER;
            default: throw new IllegalArgumentException("Unknown transaction type: " + type);
        }
    }

    private Transaction.TransactionType mapTransactionType(TransactionEntity.TransactionType type) {
        if (type == null) return null;
        switch (type) {
            case DEPOSIT: return Transaction.TransactionType.DEPOSIT;
            case WITHDRAWAL: return Transaction.TransactionType.WITHDRAWAL;
            case TRANSFER: return Transaction.TransactionType.TRANSFER;
            default: throw new IllegalArgumentException("Unknown transaction type: " + type);
        }
    }

    private TransactionEntity.TransactionStatus mapTransactionStatus(Transaction.TransactionStatus status) {
        if (status == null) return null;
        switch (status) {
            case PENDING: return TransactionEntity.TransactionStatus.PENDING;
            case COMPLETED: return TransactionEntity.TransactionStatus.COMPLETED;
            case FAILED: return TransactionEntity.TransactionStatus.FAILED;
            default: throw new IllegalArgumentException("Unknown transaction status: " + status);
        }
    }

    private Transaction.TransactionStatus mapTransactionStatus(TransactionEntity.TransactionStatus status) {
        if (status == null) return null;
        switch (status) {
            case PENDING: return Transaction.TransactionStatus.PENDING;
            case COMPLETED: return Transaction.TransactionStatus.COMPLETED;
            case FAILED: return Transaction.TransactionStatus.FAILED;
            default: throw new IllegalArgumentException("Unknown transaction status: " + status);
        }
    }
}