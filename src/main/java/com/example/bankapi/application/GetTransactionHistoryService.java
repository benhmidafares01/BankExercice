package com.example.bankapi.application;

import com.example.bankapi.domain.exception.AccountNotFoundException;
import com.example.bankapi.domain.model.Transaction;
import com.example.bankapi.domain.port.AccountRepository;
import com.example.bankapi.domain.port.TransactionRepository;
import com.example.bankapi.domain.usecase.GetTransactionHistoryUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetTransactionHistoryService implements GetTransactionHistoryUseCase {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public List<Transaction> getTransactionHistory(UUID accountId) {

        if (!accountRepository.findById(accountId).isPresent()) {
            throw new AccountNotFoundException("Account not found");
        }

        return transactionRepository.findBySourceAccountIdOrTargetAccountId(accountId, accountId);
    }
}
