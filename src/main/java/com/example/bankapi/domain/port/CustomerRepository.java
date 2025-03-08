package com.example.bankapi.domain.port;

import com.example.bankapi.domain.model.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(Customer customer);
    Optional<Customer> findById(UUID id);
    boolean existsById(UUID id);
}
