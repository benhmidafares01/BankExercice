package com.example.bankapi.domain.model;

import com.example.bankapi.domain.exception.InsufficientFundsException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void hasEnoughBalance_shouldReturnTrue_whenBalanceIsGreaterThanAmount() {
        // Arrange
        Account account = Account.builder()
                .id(UUID.randomUUID())
                .balance(new BigDecimal("100.00"))
                .build();

        // Act & Assert
        assertTrue(account.hasEnoughBalance(new BigDecimal("50.00")));
    }

    @Test
    void hasEnoughBalance_shouldReturnTrue_whenBalanceIsEqualToAmount() {
        // Arrange
        Account account = Account.builder()
                .id(UUID.randomUUID())
                .balance(new BigDecimal("100.00"))
                .build();

        // Act & Assert
        assertTrue(account.hasEnoughBalance(new BigDecimal("100.00")));
    }

    @Test
    void hasEnoughBalance_shouldReturnFalse_whenBalanceIsLessThanAmount() {
        // Arrange
        Account account = Account.builder()
                .id(UUID.randomUUID())
                .balance(new BigDecimal("50.00"))
                .build();

        // Act & Assert
        assertFalse(account.hasEnoughBalance(new BigDecimal("100.00")));
    }

    @Test
    void deposit_shouldIncreaseBalance() {
        // Arrange
        Account account = Account.builder()
                .id(UUID.randomUUID())
                .balance(new BigDecimal("100.00"))
                .build();

        // Act
        account.deposit(new BigDecimal("50.00"));

        // Assert
        assertEquals(new BigDecimal("150.00"), account.getBalance());
    }

    @Test
    void withdraw_shouldDecreaseBalance_whenSufficientFunds() {
        // Arrange
        Account account = Account.builder()
                .id(UUID.randomUUID())
                .balance(new BigDecimal("100.00"))
                .build();

        // Act
        account.withdraw(new BigDecimal("50.00"));

        // Assert
        assertEquals(new BigDecimal("50.00"), account.getBalance());
    }

    @Test
    void withdraw_shouldThrowException_whenInsufficientFunds() {
        // Arrange
        Account account = Account.builder()
                .id(UUID.randomUUID())
                .balance(new BigDecimal("50.00"))
                .build();

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(new BigDecimal("100.00"));
        });
    }
}