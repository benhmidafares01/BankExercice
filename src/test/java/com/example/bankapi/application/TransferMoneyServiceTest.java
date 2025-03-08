package com.example.bankapi.application;

import com.example.bankapi.domain.exception.AccountNotFoundException;
import com.example.bankapi.domain.exception.InsufficientFundsException;
import com.example.bankapi.domain.model.Account;
import com.example.bankapi.domain.model.Transaction;
import com.example.bankapi.domain.port.AccountRepository;
import com.example.bankapi.domain.port.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransferMoneyServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    private TransferMoneyService transferMoneyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transferMoneyService = new TransferMoneyService(accountRepository, transactionRepository);
    }

    @Test
    void transferMoney_shouldTransferMoneyBetweenAccounts_whenValidTransfer() {
        // Arrange
        UUID sourceAccountId = UUID.randomUUID();
        UUID targetAccountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("50.00");

        Account sourceAccount = Account.builder()
                .id(sourceAccountId)
                .balance(new BigDecimal("100.00"))
                .build();

        Account targetAccount = Account.builder()
                .id(targetAccountId)
                .balance(new BigDecimal("200.00"))
                .build();

        when(accountRepository.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findById(targetAccountId)).thenReturn(Optional.of(targetAccount));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Transaction result = transferMoneyService.transferMoney(sourceAccountId, targetAccountId, amount);

        // Assert
        assertNotNull(result);
        assertEquals(sourceAccountId, result.getSourceAccountId());
        assertEquals(targetAccountId, result.getTargetAccountId());
        assertEquals(amount, result.getAmount());
        assertEquals(Transaction.TransactionType.TRANSFER, result.getType());
        assertEquals(Transaction.TransactionStatus.COMPLETED, result.getStatus());

        // Verify
        ArgumentCaptor<Account> accountCaptor = ArgumentCaptor.forClass(Account.class);
        verify(accountRepository, times(2)).save(accountCaptor.capture());

        assertEquals(new BigDecimal("50.00"), sourceAccount.getBalance());
        assertEquals(new BigDecimal("250.00"), targetAccount.getBalance());

        // Verify transaction was created and saved twice
        verify(transactionRepository, times(2)).save(any(Transaction.class));
    }

    @Test
    void transferMoney_shouldThrowException_whenSourceAccountNotFound() {
        // Arrange
        UUID sourceAccountId = UUID.randomUUID();
        UUID targetAccountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("50.00");

        when(accountRepository.findById(sourceAccountId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AccountNotFoundException.class, () -> {
            transferMoneyService.transferMoney(sourceAccountId, targetAccountId, amount);
        });

        // Verify no accounts were saved
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void transferMoney_shouldThrowException_whenTargetAccountNotFound() {
        // Arrange
        UUID sourceAccountId = UUID.randomUUID();
        UUID targetAccountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("50.00");

        Account sourceAccount = Account.builder()
                .id(sourceAccountId)
                .balance(new BigDecimal("100.00"))
                .build();

        when(accountRepository.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findById(targetAccountId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AccountNotFoundException.class, () -> {
            transferMoneyService.transferMoney(sourceAccountId, targetAccountId, amount);
        });

        // Verify no accounts were saved
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void transferMoney_shouldThrowException_whenInsufficientFunds() {
        // Arrange
        UUID sourceAccountId = UUID.randomUUID();
        UUID targetAccountId = UUID.randomUUID();
        BigDecimal amount = new BigDecimal("150.00");

        Account sourceAccount = Account.builder()
                .id(sourceAccountId)
                .balance(new BigDecimal("100.00"))
                .build();

        Account targetAccount = Account.builder()
                .id(targetAccountId)
                .balance(new BigDecimal("200.00"))
                .build();

        when(accountRepository.findById(sourceAccountId)).thenReturn(Optional.of(sourceAccount));
        when(accountRepository.findById(targetAccountId)).thenReturn(Optional.of(targetAccount));

        // Act & Assert
        assertThrows(InsufficientFundsException.class, () -> {
            transferMoneyService.transferMoney(sourceAccountId, targetAccountId, amount);
        });

        // Verify no transactions were saved
        verify(transactionRepository, never()).save(any(Transaction.class));

        // Verify account states weren't changed
        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void transferMoney_shouldThrowException_whenZeroAmount() {
        // Arrange
        UUID sourceAccountId = UUID.randomUUID();
        UUID targetAccountId = UUID.randomUUID();
        BigDecimal amount = BigDecimal.ZERO;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            transferMoneyService.transferMoney(sourceAccountId, targetAccountId, amount);
        });

        // Verify no interactions with repositories
        verifyNoInteractions(accountRepository, transactionRepository);
    }
}
