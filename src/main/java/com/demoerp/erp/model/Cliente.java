package com.demoerp.erp.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "cpf_cnpj", nullable = false, unique = true)
    private String cpfCnpj;

    @Column(nullable = false)
    private String endereco;

    private String telefone;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;
} 