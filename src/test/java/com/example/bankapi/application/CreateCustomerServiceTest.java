package com.example.bankapi.application;

import com.example.bankapi.domain.model.Customer;
import com.example.bankapi.domain.port.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateCustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private CreateCustomerService createCustomerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        createCustomerService = new CreateCustomerService(customerRepository);
    }

    @Test
    void createCustomer_shouldCreateAndSaveCustomer() {
        // Arrange
        String customerName = "John Doe";
        String customerEmail = "john.doe@example.com";

        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Customer result = createCustomerService.createCustomer(customerName, customerEmail);

        // Assert
        assertNotNull(result);
        assertEquals(customerName, result.getName());
        assertEquals(customerEmail, result.getEmail());

        // Verify
        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(customerCaptor.capture());

        Customer capturedCustomer = customerCaptor.getValue();
        assertEquals(customerName, capturedCustomer.getName());
        assertEquals(customerEmail, capturedCustomer.getEmail());
    }
}
