package com.payflow.payflow_gateway.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateMerchantDTO(
        @NotBlank(message = "O nome é obrigatório") String name,
        @NotBlank(message = "O email é obrigatório") @Email(message = "Formato de e-mail inválido") String email
) {}