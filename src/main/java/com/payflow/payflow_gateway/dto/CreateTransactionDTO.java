package com.payflow.payflow_gateway.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;

public record CreateTransactionDTO(
        @NotNull(message = "O ID do estabelecimento é obrigatório") UUID merchantId,
        @NotNull(message = "O valor é obrigatório") @Positive(message = "O valor da transação deve ser maior que zero") Integer amount,
        @Email(message = "Formato de e-mail inválido") String customerEmail
) {}