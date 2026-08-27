package com.payflow.payflow_gateway.service;

import com.payflow.payflow_gateway.domain.Merchant;
import com.payflow.payflow_gateway.domain.Transaction;
import com.payflow.payflow_gateway.domain.TransactionStatus;
import com.payflow.payflow_gateway.dto.CreateTransactionDTO;
import com.payflow.payflow_gateway.repository.MerchantRepository;
import com.payflow.payflow_gateway.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private MerchantRepository merchantRepository;

    @InjectMocks
    private TransactionService service;

    @Test
    void shouldCreateTransactionSuccessfully() {
        UUID merchantId = UUID.randomUUID();
        String idempotencyKey = UUID.randomUUID().toString();
        CreateTransactionDTO dto = new CreateTransactionDTO(merchantId, 10000, "cliente@teste.com", idempotencyKey);

        Merchant activeMerchant = new Merchant();
        activeMerchant.setId(merchantId);
        activeMerchant.setActive(true);

        Transaction savedTransaction = new Transaction();
        savedTransaction.setId(UUID.randomUUID());
        savedTransaction.setMerchant(activeMerchant);
        savedTransaction.setAmount(dto.amount());
        savedTransaction.setIdempotencyKey(idempotencyKey);
        savedTransaction.setStatus(TransactionStatus.PENDING);

        when(transactionRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(false);
        when(merchantRepository.findById(merchantId)).thenReturn(Optional.of(activeMerchant));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        Transaction result = service.create(dto);

        assertEquals(TransactionStatus.PENDING, result.getStatus());
        assertEquals(10000, result.getAmount());
        assertEquals(idempotencyKey, result.getIdempotencyKey());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void shouldThrowExceptionWhenIdempotencyKeyAlreadyExists() {
        UUID merchantId = UUID.randomUUID();
        String idempotencyKey = "chave-duplicada-123";
        CreateTransactionDTO dto = new CreateTransactionDTO(merchantId, 10000, "cliente@teste.com", idempotencyKey);

        when(transactionRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.create(dto));
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        verify(merchantRepository, never()).findById(any());
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void shouldThrowExceptionWhenMerchantNotFound() {
        String idempotencyKey = UUID.randomUUID().toString();
        CreateTransactionDTO dto = new CreateTransactionDTO(UUID.randomUUID(), 10000, "cliente@teste.com", idempotencyKey);

        when(transactionRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(false);
        when(merchantRepository.findById(dto.merchantId())).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> service.create(dto));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void shouldThrowExceptionWhenMerchantIsInactive() {
        UUID merchantId = UUID.randomUUID();
        String idempotencyKey = UUID.randomUUID().toString();
        CreateTransactionDTO dto = new CreateTransactionDTO(merchantId, 10000, "cliente@teste.com", idempotencyKey);

        Merchant inactiveMerchant = new Merchant();
        inactiveMerchant.setId(merchantId);
        inactiveMerchant.setActive(false);

        when(transactionRepository.existsByIdempotencyKey(idempotencyKey)).thenReturn(false);
        when(merchantRepository.findById(merchantId)).thenReturn(Optional.of(inactiveMerchant));

        assertThrows(ResponseStatusException.class, () -> service.create(dto));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }
}