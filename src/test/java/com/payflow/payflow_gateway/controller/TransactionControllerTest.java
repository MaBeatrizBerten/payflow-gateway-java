package com.payflow.payflow_gateway.controller;

import tools.jackson.databind.ObjectMapper;
import com.payflow.payflow_gateway.domain.Merchant;
import com.payflow.payflow_gateway.dto.CreateTransactionDTO;
import com.payflow.payflow_gateway.repository.MerchantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MerchantRepository merchantRepository;

    private Merchant testMerchant;

    @BeforeEach
    void setup() {
        // Prepara um Merchant válido no banco antes de rodar o teste
        Merchant merchant = new Merchant();
        merchant.setName("Merchant Integração");
        merchant.setEmail("integracao@teste.com");
        merchant.setActive(true);
        testMerchant = merchantRepository.save(merchant);
    }

    @Test
    void shouldCreateTransactionViaHttp() throws Exception {
        String idempotencyKey = UUID.randomUUID().toString();
        CreateTransactionDTO dto = new CreateTransactionDTO(testMerchant.getId(), 7500, "cliente@teste.com", idempotencyKey);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.amount").value(7500))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void shouldReturnBadRequestWhenValidationFails() throws Exception {
        // Envia um valor negativo (inválido) e sem chave de idempotência
        CreateTransactionDTO dto = new CreateTransactionDTO(testMerchant.getId(), -100, "invalido", "");

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Erro de validação"));
    }
}