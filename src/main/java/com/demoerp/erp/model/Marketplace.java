package com.demoerp.erp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "marketplaces")
public class Marketplace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "taxa_comissao")
    private BigDecimal taxaComissao;

    @Column(name = "politicas_envio", columnDefinition = "TEXT")
    private String politicasEnvio;

    @ManyToMany(mappedBy = "marketplaces")
    private List<Loja> lojas;

    @ManyToMany
    @JoinTable(
        name = "marketplace_produto",
        joinColumns = @JoinColumn(name = "marketplace_id"),
        inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produto> produtos;

    @ManyToMany
    @JoinTable(
        name = "marketplace_estoque",
        joinColumns = @JoinColumn(name = "marketplace_id"),
        inverseJoinColumns = @JoinColumn(name = "estoque_id")
    )
    private List<Estoque> estoques;
} 