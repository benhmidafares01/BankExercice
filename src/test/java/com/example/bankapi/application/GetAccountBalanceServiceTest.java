package com.example.bankapi.application;

import com.example.bankapi.domain.exception.AccountNotFoundException;
import com.example.bankapi.domain.model.Account;
import com.example.bankapi.domain.port.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GetAccountBalanceServiceTest {

    @Mock
    private AccountRepository accountRepository;

    private GetAccountBalanceService getAccountBalanceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        getAccountBalanceService = new GetAccountBalanceService(accountRepository);
    }

    @Test
    void getBalance_shouldReturnAccountBalance_whenAccountExists() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        BigDecimal expectedBalance = new BigDecimal("100.00");

        Account account = Account.builder()
                .id(accountId)
                .balance(expectedBalance)
                .build();

        when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        // Act
        BigDecimal result = getAccountBalanceService.getBalance(accountId);

        // Assert
        assertEquals(expectedBalance, result);
    }

    @Test
    void getBalance_shouldThrowException_whenAccountNotFound() {
        // Arrange
        UUID accountId = UUID.randomUUID();
        when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AccountNotFoundException.class, () -> {
            getAccountBalanceService.getBalance(accountId);
        });
    }
}