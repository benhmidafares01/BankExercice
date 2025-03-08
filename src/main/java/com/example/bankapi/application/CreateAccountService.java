package com.example.bankapi.application;

import com.example.bankapi.domain.exception.CustomerNotFoundException;
import com.example.bankapi.domain.model.Account;
import com.example.bankapi.domain.model.Transaction;
import com.example.bankapi.domain.port.AccountRepository;
import com.example.bankapi.domain.port.CustomerRepository;
import com.example.bankapi.domain.port.TransactionRepository;
import com.example.bankapi.domain.usecase.CreateAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateAccountService implements CreateAccountUseCase {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Account createAccount(UUID customerId, BigDecimal initialDeposit) {
        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }

        if (initialDeposit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial deposit cannot be negative");
        }

        Account account = Account.builder()
                .id(UUID.randomUUID())
                .customerId(customerId)
                .accountNumber(generateAccountNumber())
                .balance(initialDeposit)
                .createdAt(LocalDateTime.now())
                .build();

        Account savedAccount = accountRepository.save(account);

        if (initialDeposit.compareTo(BigDecimal.ZERO) > 0) {
            Transaction transaction = Transaction.builder()
                    .id(UUID.randomUUID())
                    .targetAccountId(savedAccount.getId())
                    .amount(initialDeposit)
                    .timestamp(LocalDateTime.now())
                    .type(Transaction.TransactionType.DEPOSIT)
                    .status(Transaction.TransactionStatus.COMPLETED)
                    .build();

            transactionRepository.save(transaction);
        }

        return savedAccount;
    }

    private String generateAccountNumber() {
        return "ACCT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
