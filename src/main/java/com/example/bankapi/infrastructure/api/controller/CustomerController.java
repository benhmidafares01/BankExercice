package com.example.bankapi.infrastructure.api.controller;

import com.example.bankapi.domain.model.Customer;
import com.example.bankapi.domain.usecase.CreateCustomerUseCase;
import com.example.bankapi.domain.usecase.GetCustomerUseCase;
import com.example.bankapi.infrastructure.api.dto.CustomerRequest;
import com.example.bankapi.infrastructure.api.dto.CustomerResponse;
import com.example.bankapi.infrastructure.api.mapper.CustomerDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customer API", description = "API endpoints for customer management")
public class CustomerController {

    private final CreateCustomerUseCase createCustomerUseCase;
    private final GetCustomerUseCase getCustomerUseCase;
    private final CustomerDtoMapper customerDtoMapper;

    @PostMapping
    @Operation(summary = "Create a new customer", description = "Creates a new customer")
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {
        Customer customer = createCustomerUseCase.createCustomer(request.getName(), request.getEmail());
        CustomerResponse response = customerDtoMapper.toResponse(customer);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{customerId}")
    @Operation(summary = "Get customer by ID", description = "Retrieves a customer by their ID")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable UUID customerId) {
        Customer customer = getCustomerUseCase.getCustomer(customerId).orElseThrow();
        CustomerResponse response = customerDtoMapper.toResponse(customer);
        return ResponseEntity.ok(response);
    }
}
