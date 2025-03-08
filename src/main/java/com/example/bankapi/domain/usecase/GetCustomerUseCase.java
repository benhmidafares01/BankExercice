package com.example.bankapi.domain.usecase;

import com.example.bankapi.domain.model.Customer;

import java.util.Optional;
import java.util.UUID;

public interface GetCustomerUseCase {

    Optional<Customer> getCustomer(UUID customerId);
}