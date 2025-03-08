package com.example.bankapi.application;

import com.example.bankapi.domain.exception.AccountNotFoundException;
import com.example.bankapi.domain.model.Account;
import com.example.bankapi.domain.model.Transaction;
import com.example.bankapi.domain.port.AccountRepository;
import com.example.bankapi.domain.port.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetTransactionHistoryServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    private GetTransactionHistoryService getTransactionHistoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        getTransactionHistoryService = new GetTransactionHistoryService(accountRepository, transactionRepository);
    }

    @Test
    void getTransactionHistory_shouldReturnTransactionList_whenAccountExistsAndHasTransactions() {
        // Arrange
        UUID accountId = UUID.randomUUID();

        Account account = Account.builder()
                .id(accountId)
                .build();

        Transaction transaction1 = Transaction.builder()
                .id(UUID.randomUUID())
                .sourceAccountId(accountId)
                .build();

        Transaction transaction2 = Transaction.builder()
                .id(UUID.randomUUID())
                .targetAccountId(accountId)
                .build();

        List<Transaction> expectedTransactions = Arrays.asList(transaction1, transaction2);

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(transactionRepository.findBySourceAccountIdOrTargetAccountId(accountId, accountId)).thenReturn(expectedTransactions);

        // Act
        List<Transaction> result = getTransactionHistoryService.getTransactionHistory(accountId);

        // Assert
        assertEquals(expectedTransactions.size(), result.size());
        assertTrue(result.containsAll(expectedTransactions));
    }

    @Test
    void getTransactionHistory_shouldReturnEmptyList_whenAccountExistsButHasNoTransactions() {
        // Arrange
        UUID accountId = UUID.randomUUID();

        Account account = Account.builder()
                .id(accountId)
                .build();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        when(transactionRepository.findBySourceAccountIdOrTargetAccountId(accountId, accountId)).thenReturn(Collections.emptyList());

        // Act
        List<Transaction> result = getTransactionHistoryService.getTransactionHistory(accountId);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getTransactionHistory_shouldThrowException_whenAccountNotFound() {
        // Arrange
        UUID accountId = UUID.randomUUID();

        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AccountNotFoundException.class, () -> {
            getTransactionHistoryService.getTransactionHistory(accountId);
        });

        // Verify
        verify(transactionRepository, never()).findBySourceAccountIdOrTargetAccountId(any(), any());
    }
}