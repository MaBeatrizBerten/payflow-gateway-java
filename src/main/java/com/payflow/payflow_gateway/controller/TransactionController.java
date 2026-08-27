package com.payflow.payflow_gateway.controller;

import com.payflow.payflow_gateway.domain.Transaction;
import com.payflow.payflow_gateway.dto.CreateTransactionDTO;
import com.payflow.payflow_gateway.dto.UpdateTransactionStatusDTO;
import com.payflow.payflow_gateway.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Transaction> create(@RequestBody @Valid CreateTransactionDTO dto) {
        Transaction created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> findById(@PathVariable UUID id) {
        Transaction transaction = service.findById(id);
        return ResponseEntity.ok(transaction);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Transaction> updateStatus(@PathVariable UUID id, @RequestBody @Valid UpdateTransactionStatusDTO dto) {
        Transaction updated = service.updateStatus(id, dto.status());
        return ResponseEntity.ok(updated);
    }
}