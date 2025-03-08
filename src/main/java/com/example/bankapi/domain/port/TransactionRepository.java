package com.example.bankapi.domain.port;

import com.example.bankapi.domain.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    List<Transaction> findBySourceAccountIdOrTargetAccountId(UUID accountId, UUID sameAccountId);
}