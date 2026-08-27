package com.payflow.payflow_gateway.service;

import com.payflow.payflow_gateway.domain.Merchant;
import com.payflow.payflow_gateway.dto.CreateMerchantDTO;
import com.payflow.payflow_gateway.repository.MerchantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MerchantServiceTest {

    @Mock
    private MerchantRepository repository;

    @InjectMocks
    private MerchantService service;

    @Test
    void shouldCreateMerchantSuccessfully() {
        // Arrange (Prepara os dados)
        CreateMerchantDTO dto = new CreateMerchantDTO("Empresa Teste", "teste@empresa.com");
        Merchant savedMerchant = new Merchant();
        savedMerchant.setId(UUID.randomUUID());
        savedMerchant.setName(dto.name());
        savedMerchant.setEmail(dto.email());
        savedMerchant.setActive(true);

        when(repository.save(any(Merchant.class))).thenReturn(savedMerchant);

        // Act (Executa a ação)
        Merchant result = service.create(dto);

        // Assert (Verifica o resultado)
        assertNotNull(result.getId());
        assertEquals("Empresa Teste", result.getName());
        assertTrue(result.getActive());
        verify(repository, times(1)).save(any(Merchant.class));
    }
}