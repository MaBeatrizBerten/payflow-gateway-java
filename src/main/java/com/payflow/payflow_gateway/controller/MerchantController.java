package com.payflow.payflow_gateway.controller;

import com.payflow.payflow_gateway.domain.Merchant;
import com.payflow.payflow_gateway.dto.CreateMerchantDTO;
import com.payflow.payflow_gateway.service.MerchantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/merchants")
public class MerchantController {

    private final MerchantService service;

    public MerchantController(MerchantService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Merchant> create(@RequestBody @Valid CreateMerchantDTO dto) {
        Merchant created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}