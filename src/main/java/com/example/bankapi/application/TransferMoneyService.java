package com.example.bankapi.application;

import com.example.bankapi.domain.exception.AccountNotFoundException;
import com.example.bankapi.domain.exception.InsufficientFundsException;
import com.example.bankapi.domain.model.Account;
import com.example.bankapi.domain.model.Transaction;
import com.example.bankapi.domain.port.AccountRepository;
import com.example.bankapi.domain.port.TransactionRepository;
import com.example.bankapi.domain.usecase.TransferMoneyUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransferMoneyService implements TransferMoneyUseCase {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Transaction transferMoney(UUID sourceAccountId, UUID targetAccountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        Account sourceAccount = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new AccountNotFoundException("Source account not found"));

        Account targetAccount = accountRepository.findById(targetAccountId)
                .orElseThrow(() -> new AccountNotFoundException("Target account not found"));

        if (!sourceAccount.hasEnoughBalance(amount)) {
            throw new InsufficientFundsException("Insufficient funds in source account");
        }


        Transaction transaction = Transaction.builder()
                .id(UUID.randomUUID())
                .sourceAccountId(sourceAccountId)
                .targetAccountId(targetAccountId)
                .amount(amount)
                .timestamp(LocalDateTime.now())
                .type(Transaction.TransactionType.TRANSFER)
                .status(Transaction.TransactionStatus.PENDING)
                .build();

        Transaction savedTransaction = transactionRepository.save(transaction);

        try {

            sourceAccount.withdraw(amount);
            targetAccount.deposit(amount);

            accountRepository.save(sourceAccount);
            accountRepository.save(targetAccount);

            savedTransaction.setStatus(Transaction.TransactionStatus.COMPLETED);
            return transactionRepository.save(savedTransaction);

        } catch (Exception e) {
            savedTransaction.setStatus(Transaction.TransactionStatus.FAILED);
            transactionRepository.save(savedTransaction);
            throw e;
        }
    }
}

