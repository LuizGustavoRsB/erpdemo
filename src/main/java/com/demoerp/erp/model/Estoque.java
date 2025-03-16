package com.demoerp.erp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "estoques")
public class Estoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String localizacao;

    @Column(name = "quantidade_total")
    private Integer quantidadeTotal;

    @OneToMany(mappedBy = "estoque")
    private List<Produto> produtos;

    @OneToOne(mappedBy = "estoque")
    private Loja loja;

    @ManyToMany(mappedBy = "estoques")
    private List<Marketplace> marketplaces;
} 