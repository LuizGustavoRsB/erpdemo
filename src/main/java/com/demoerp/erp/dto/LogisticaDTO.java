package com.demoerp.erp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LogisticaDTO {
    private Long id;

    @NotBlank(message = "O método de envio é obrigatório")
    private String metodoEnvio;

    @NotBlank(message = "A transportadora é obrigatória")
    private String transportadora;

    private String codigoRastreio;

    private String statusEntrega;

    @NotNull(message = "O pedido é obrigatório")
    private Long pedidoId;
} 