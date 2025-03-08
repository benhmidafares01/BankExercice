package com.example.bankapi.domain.usecase;

import com.example.bankapi.domain.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface GetTransactionHistoryUseCase {
    List<Transaction> getTransactionHistory(UUID accountId);
}