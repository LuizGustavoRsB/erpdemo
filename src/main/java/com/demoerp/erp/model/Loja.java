package com.demoerp.erp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "lojas")
public class Loja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cnpj;

    @Column(nullable = false)
    private String endereco;

    private String telefone;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "loja")
    private List<Pedido> pedidos;

    @OneToOne
    private Estoque estoque;

    @ManyToMany
    @JoinTable(
        name = "loja_marketplace",
        joinColumns = @JoinColumn(name = "loja_id"),
        inverseJoinColumns = @JoinColumn(name = "marketplace_id")
    )
    private List<Marketplace> marketplaces;
} 