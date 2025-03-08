package com.example.bankapi.infrastructure.api.controller;

import com.example.bankapi.domain.model.Account;
import com.example.bankapi.domain.usecase.CreateAccountUseCase;
import com.example.bankapi.domain.usecase.GetAccountBalanceUseCase;
import com.example.bankapi.infrastructure.api.dto.AccountResponse;
import com.example.bankapi.infrastructure.api.dto.BalanceResponse;
import com.example.bankapi.infrastructure.api.dto.CreateAccountRequest;
import com.example.bankapi.infrastructure.api.mapper.AccountDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Account API", description = "API endpoints for account management")
public class AccountController {

    private final CreateAccountUseCase createAccountUseCase;
    private final GetAccountBalanceUseCase getAccountBalanceUseCase;
    private final AccountDtoMapper accountDtoMapper;

    @PostMapping
    @Operation(summary = "Create a new account", description = "Creates a new account for a customer with an initial deposit")
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        Account account = createAccountUseCase.createAccount(request.getCustomerId(), request.getInitialDeposit());
        return new ResponseEntity<>(accountDtoMapper.toResponse(account), HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}/balance")
    @Operation(summary = "Get account balance", description = "Retrieves the current balance for a given account")
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable UUID accountId) {
        return ResponseEntity.ok(
                BalanceResponse.builder()
                        .accountId(accountId)
                        .balance(getAccountBalanceUseCase.getBalance(accountId))
                        .build()
        );
    }
}
