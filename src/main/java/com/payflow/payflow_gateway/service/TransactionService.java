package com.payflow.payflow_gateway.service;

import com.payflow.payflow_gateway.domain.Merchant;
import com.payflow.payflow_gateway.domain.Transaction;
import com.payflow.payflow_gateway.domain.TransactionStatus;
import com.payflow.payflow_gateway.dto.CreateTransactionDTO;
import com.payflow.payflow_gateway.repository.MerchantRepository;
import com.payflow.payflow_gateway.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final MerchantRepository merchantRepository;

    public TransactionService(TransactionRepository transactionRepository, MerchantRepository merchantRepository) {
        this.transactionRepository = transactionRepository;
        this.merchantRepository = merchantRepository;
    }

    @Transactional
    public Transaction create(CreateTransactionDTO dto) {
        Merchant merchant = merchantRepository.findById(dto.merchantId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Merchant não encontrado"));

        if (!Boolean.TRUE.equals(merchant.getActive())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Merchant inativo");
        }

        Transaction transaction = new Transaction();
        transaction.setMerchant(merchant);
        transaction.setAmount(dto.amount());
        transaction.setCustomerEmail(dto.customerEmail());
        transaction.setStatus(TransactionStatus.PENDING);

        return transactionRepository.save(transaction);
    }
}