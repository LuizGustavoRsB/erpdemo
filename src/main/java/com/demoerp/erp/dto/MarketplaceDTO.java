package com.demoerp.erp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
public class MarketplaceDTO {
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String nome;

    @NotNull(message = "A taxa de comissão é obrigatória")
    @PositiveOrZero(message = "A taxa de comissão não pode ser negativa")
    private BigDecimal taxaComissao;

    private String politicasEnvio;

    private List<Long> lojaIds;
    
    private List<Long> produtoIds;
    
    private List<Long> estoqueIds;
} 