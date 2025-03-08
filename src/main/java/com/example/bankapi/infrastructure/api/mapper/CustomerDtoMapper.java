package com.example.bankapi.infrastructure.api.mapper;

import com.example.bankapi.domain.model.Customer;
import com.example.bankapi.infrastructure.api.dto.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerDtoMapper {
    public CustomerResponse toResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .email(customer.getEmail())
                .build();
    }
}
