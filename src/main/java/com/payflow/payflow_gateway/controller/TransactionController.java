package com.payflow.payflow_gateway.controller;

import com.payflow.payflow_gateway.domain.Transaction;
import com.payflow.payflow_gateway.dto.CreateTransactionDTO;
import com.payflow.payflow_gateway.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}