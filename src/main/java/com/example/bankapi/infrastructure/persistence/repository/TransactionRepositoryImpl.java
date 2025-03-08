package com.example.bankapi.infrastructure.persistence.repository;

import com.example.bankapi.domain.model.Transaction;
import com.example.bankapi.domain.port.TransactionRepository;
import com.example.bankapi.infrastructure.persistence.jpa.TransactionJpaRepository;
import com.example.bankapi.infrastructure.persistence.mapper.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionJpaRepository jpaRepository;
    private final TransactionMapper mapper;

    @Override
    public Transaction save(Transaction transaction) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(transaction)));
    }

    @Override
    public List<Transaction> findBySourceAccountIdOrTargetAccountId(UUID sourceAccountId, UUID targetAccountId) {
        return jpaRepository.findBySourceAccountIdOrTargetAccountId(sourceAccountId, targetAccountId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
