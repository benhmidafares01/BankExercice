package com.example.bankapi.application;

import com.example.bankapi.domain.exception.CustomerNotFoundException;
import com.example.bankapi.domain.model.Account;
import com.example.bankapi.domain.model.Transaction;
import com.example.bankapi.domain.port.AccountRepository;
import com.example.bankapi.domain.port.CustomerRepository;
import com.example.bankapi.domain.port.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateAccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransactionRepository transactionRepository;

    private CreateAccountService createAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createAccountService = new CreateAccountService(accountRepository, customerRepository, transactionRepository);
    }

    @Test
    void createAccount_shouldCreateAccountAndInitialTransaction_whenValidCustomerAndPositiveDeposit() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        BigDecimal initialDeposit = new BigDecimal("100.00");

        when(customerRepository.existsById(customerId)).thenReturn(true);
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Account result = createAccountService.createAccount(customerId, initialDeposit);

        // Assert
        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        assertEquals(initialDeposit, result.getBalance());

        // Verify account was saved
        verify(accountRepository).save(any(Account.class));

        // Verify transaction was created and saved
        ArgumentCaptor<Transaction> transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).save(transactionCaptor.capture());

        Transaction capturedTransaction = transactionCaptor.getValue();
        assertEquals(result.getId(), capturedTransaction.getTargetAccountId());
        assertEquals(initialDeposit, capturedTransaction.getAmount());
        assertEquals(Transaction.TransactionType.DEPOSIT, capturedTransaction.getType());
        assertEquals(Transaction.TransactionStatus.COMPLETED, capturedTransaction.getStatus());
    }

    @Test
    void createAccount_shouldThrowException_whenCustomerNotFound() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        BigDecimal initialDeposit = new BigDecimal("100.00");

        when(customerRepository.existsById(customerId)).thenReturn(false);

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> {
            createAccountService.createAccount(customerId, initialDeposit);
        });

        // Verify no account or transaction was saved
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void createAccount_shouldThrowException_whenNegativeInitialDeposit() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        BigDecimal negativeDeposit = new BigDecimal("-100.00");

        when(customerRepository.existsById(customerId)).thenReturn(true);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            createAccountService.createAccount(customerId, negativeDeposit);
        });

        // Verify no account or transaction was saved
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }
}
