package com.demoerp.erp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class FinanceiroDTO {
    private Long id;

    @NotNull(message = "O valor de receitas é obrigatório")
    @PositiveOrZero(message = "O valor de receitas não pode ser negativo")
    private BigDecimal receitas;

    @NotNull(message = "O valor de despesas é obrigatório")
    @PositiveOrZero(message = "O valor de despesas não pode ser negativo")
    private BigDecimal despesas;

    @NotNull(message = "O valor de lucro é obrigatório")
    private BigDecimal lucro;

    @NotNull(message = "O valor de taxas de marketplace é obrigatório")
    @PositiveOrZero(message = "O valor de taxas não pode ser negativo")
    private BigDecimal taxasMarketplace;

    @NotNull(message = "O valor de pagamentos pendentes é obrigatório")
    @PositiveOrZero(message = "O valor de pagamentos pendentes não pode ser negativo")
    private BigDecimal pagamentosPendentes;

    private List<Long> pedidoIds;
    
    private List<Long> marketplaceIds;
} 