package com.payflow.payflow_gateway.dto;

import com.payflow.payflow_gateway.domain.TransactionStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTransactionStatusDTO(
        @NotNull(message = "O novo status é obrigatório") TransactionStatus status
) {}