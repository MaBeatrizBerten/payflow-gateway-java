package com.payflow.payflow_gateway.repository;

import com.payflow.payflow_gateway.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}