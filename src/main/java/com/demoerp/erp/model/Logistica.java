package com.demoerp.erp.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "logistica")
public class Logistica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "metodo_envio", nullable = false)
    private String metodoEnvio;

    @Column(nullable = false)
    private String transportadora;

    @Column(name = "codigo_rastreio")
    private String codigoRastreio;

    @Column(name = "status_entrega")
    private String statusEntrega;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;
} 