# Bank API

## Description

This is a REST API for a simple banking application. It allows bank employees to:

*   Create new bank accounts for customers.
*   Transfer amounts between any two accounts.
*   Retrieve balances for a given account.
*   Retrieve transfer history for a given account.
*   Create and retrieve customer information.

This project uses Java, Spring Boot, H2 database and includes Swagger documentation and Lombok.

## Features

*   **Account Management:**
    *   Create a new bank account for a customer, including an initial deposit.
    *   A single customer can have multiple accounts.
*   **Money Transfer:**
    *   Transfer funds between any two accounts (including those belonging to different customers).
*   **Balance Inquiry:**
    *   Retrieve the current balance of a given account.
*   **Transaction History:**
    *   Retrieve the transaction history (transfers) for a given account.
*   **Customer Management:**
    * Create a customer.
    *   Retrieve a customer.
    
## Technologies Used

*   Java
*   Spring Boot
*   Spring Data JPA
*   H2 Database
*   Lombok
*   SpringDoc OpenAPI (Swagger)

## Architecture

The application follows a hexagonal architecture:

*   **Domain:** Contains the core business logic and domain models (Customer, Account, Transfer).
*   **Application:** Contains the use case implementations and orchestrates the domain logic.
*   **Infrastructure:** Contains the adapters for external dependencies (API controllers, JPA repositories, configuration).

## Getting Started

1.  **Clone the Repository:**

    ```bash
    git clone https://github.com/benhmidafares01/BankExercice.git
    cd BankExercice
    ```

2.  **Build the Project:**

    ```bash
    ./mvnw clean install 
    ```

3.  **Run the Application:**

    ```bash
    ./mvnw spring-boot:run
    ```

    The application will start on port 8080 by default.

**API Documentation (Swagger):**

Access the Swagger UI to explore the API endpoints:

[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

**H2 Console:**

The H2 database console is available for inspecting the data during development.

[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

*   **JDBC URL:** `jdbc:h2:mem:testdb`
*   **User Name:** `sa`
*   **Password:** (leave blank)

**API Endpoints:**

*   **Customers:**
    *   `POST /api/customers`: Create a new customer.
    *   `GET /api/customers/{customerId}`: Get a customer by ID.
*   **Accounts:**
    *   `POST /api/accounts`: Create a new account for a customer.
    *   `GET /api/accounts/{accountId}/balance`: Get the balance of an account.
*   **Transactions:**
    *   `POST /api/transactions/transfer`: Transfer money between two accounts.
    *   `GET /api/transactions/account/{accountId}`: Get the transaction history for an account.
