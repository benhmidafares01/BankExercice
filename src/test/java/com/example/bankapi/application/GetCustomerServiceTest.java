package com.example.bankapi.application;

import com.example.bankapi.domain.exception.CustomerNotFoundException;
import com.example.bankapi.domain.model.Customer;
import com.example.bankapi.domain.port.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GetCustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private GetCustomerService getCustomerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        getCustomerService = new GetCustomerService(customerRepository);
    }

    @Test
    void getCustomer_shouldReturnCustomer_whenCustomerExists() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        Customer expectedCustomer = Customer.builder()
                .id(customerId)
                .name("John Doe")
                .email("john.doe@example.com")
                .build();

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(expectedCustomer));

        // Act
        Optional<Customer> result = getCustomerService.getCustomer(customerId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expectedCustomer, result.get());
    }

    @Test
    void getCustomer_shouldThrowException_whenCustomerDoesNotExist() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> {
            getCustomerService.getCustomer(customerId);
        });
    }
}
