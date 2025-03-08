package com.example.bankapi.application;

import com.example.bankapi.domain.model.Customer;
import com.example.bankapi.domain.port.CustomerRepository;
import com.example.bankapi.domain.usecase.CreateCustomerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateCustomerService implements CreateCustomerUseCase {

    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(String name, String email) {
        Customer customer = Customer.builder()
                .id(UUID.randomUUID())
                .name(name)
                .email(email)
                .build();

        return customerRepository.save(customer);
    }
}