package com.example.bankapi.infrastructure.api.controller;

import com.example.bankapi.domain.model.Transaction;
import com.example.bankapi.domain.usecase.GetTransactionHistoryUseCase;
import com.example.bankapi.domain.usecase.TransferMoneyUseCase;
import com.example.bankapi.infrastructure.api.dto.TransactionResponse;
import com.example.bankapi.infrastructure.api.dto.TransferRequest;
import com.example.bankapi.infrastructure.api.mapper.TransactionDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transaction API", description = "API endpoints for transaction management")
public class TransactionController {

    private final TransferMoneyUseCase transferMoneyUseCase;
    private final GetTransactionHistoryUseCase getTransactionHistoryUseCase;
    private final TransactionDtoMapper transactionDtoMapper;

    @PostMapping("/transfer")
    @Operation(summary = "Transfer money", description = "Transfers money between two accounts")
    public ResponseEntity<TransactionResponse> transferMoney(@Valid @RequestBody TransferRequest request) {
        Transaction transaction = transferMoneyUseCase.transferMoney(
                request.getSourceAccountId(),
                request.getTargetAccountId(),
                request.getAmount()
        );
        return ResponseEntity.ok(transactionDtoMapper.toResponse(transaction));
    }

    @GetMapping("/account/{accountId}")
    @Operation(summary = "Get transaction history", description = "Retrieves transaction history for a given account")
    public ResponseEntity<List<TransactionResponse>> getTransactionHistory(@PathVariable UUID accountId) {
        List<Transaction> transactions = getTransactionHistoryUseCase.getTransactionHistory(accountId);
        return ResponseEntity.ok(transactionDtoMapper.toResponseList(transactions));
    }
}