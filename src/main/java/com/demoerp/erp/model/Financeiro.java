package com.demoerp.erp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "financeiro")
public class Financeiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal receitas;

    @Column(nullable = false)
    private BigDecimal despesas;

    @Column(nullable = false)
    private BigDecimal lucro;

    @Column(name = "taxas_marketplace")
    private BigDecimal taxasMarketplace;

    @Column(name = "pagamentos_pendentes")
    private BigDecimal pagamentosPendentes;

    @ManyToMany
    @JoinTable(
        name = "financeiro_pedido",
        joinColumns = @JoinColumn(name = "financeiro_id"),
        inverseJoinColumns = @JoinColumn(name = "pedido_id")
    )
    private List<Pedido> pedidos;

    @ManyToMany
    @JoinTable(
        name = "financeiro_marketplace",
        joinColumns = @JoinColumn(name = "financeiro_id"),
        inverseJoinColumns = @JoinColumn(name = "marketplace_id")
    )
    private List<Marketplace> marketplaces;
} 