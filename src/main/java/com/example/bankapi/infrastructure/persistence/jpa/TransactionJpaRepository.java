package com.example.bankapi.infrastructure.persistence.jpa;

import com.example.bankapi.infrastructure.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, UUID> {
    List<TransactionEntity> findBySourceAccountIdOrTargetAccountId(UUID sourceAccountId, UUID targetAccountId);
}
