package com.example.bankapi.domain.usecase;

import com.example.bankapi.domain.model.Customer;

public interface CreateCustomerUseCase {
    Customer createCustomer(String name, String email);
}