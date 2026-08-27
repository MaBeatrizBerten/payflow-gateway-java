package com.payflow.payflow_gateway.service;

import com.payflow.payflow_gateway.domain.Merchant;
import com.payflow.payflow_gateway.dto.CreateMerchantDTO;
import com.payflow.payflow_gateway.repository.MerchantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MerchantService {

    private final MerchantRepository repository;

    public MerchantService(MerchantRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Merchant create(CreateMerchantDTO dto) {
        Merchant merchant = new Merchant();
        merchant.setName(dto.name());
        merchant.setEmail(dto.email());
        merchant.setActive(true);
        return repository.save(merchant);
    }
}