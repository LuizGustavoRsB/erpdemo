package com.demoerp.erp.dto;

import com.demoerp.erp.model.Pedido.StatusPedido;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class PedidoDTO {
    private Long id;

    private LocalDateTime data;

    @NotNull(message = "O valor total é obrigatório")
    @Positive(message = "O valor total deve ser maior que zero")
    private BigDecimal valorTotal;

    @NotNull(message = "O status é obrigatório")
    private StatusPedido status;

    @NotNull(message = "O cliente é obrigatório")
    private Long clienteId;

    @NotNull(message = "A loja é obrigatória")
    private Long lojaId;

    @NotNull(message = "A lista de produtos é obrigatória")
    private List<Long> produtoIds;
} 