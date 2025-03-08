package com.example.bankapi.application;

import com.example.bankapi.domain.exception.CustomerNotFoundException;
import com.example.bankapi.domain.model.Customer;
import com.example.bankapi.domain.port.CustomerRepository;
import com.example.bankapi.domain.usecase.GetCustomerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetCustomerService implements GetCustomerUseCase {

    private final CustomerRepository customerRepository;

    @Override
    public Optional<Customer> getCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException("Customer not found with id : " + customerId);
        }
        return customer;
    }
}
