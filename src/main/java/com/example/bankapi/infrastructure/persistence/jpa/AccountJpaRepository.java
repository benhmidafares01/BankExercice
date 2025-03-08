package com.example.bankapi.infrastructure.persistence.jpa;


import com.example.bankapi.infrastructure.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountJpaRepository extends JpaRepository<AccountEntity, UUID> {
    List<AccountEntity> findByCustomerId(UUID customerId);
    Optional<AccountEntity> findByAccountNumber(String accountNumber);
}