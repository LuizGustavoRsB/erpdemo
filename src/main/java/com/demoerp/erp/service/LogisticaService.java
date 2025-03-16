package com.demoerp.erp.service;

import com.demoerp.erp.model.Logistica;
import java.util.List;
import java.util.Optional;

public interface LogisticaService extends CrudService<Logistica, Long> {
    Optional<Logistica> findByPedidoId(Long pedidoId);
    Optional<Logistica> findByCodigoRastreio(String codigoRastreio);
    List<Logistica> findByTransportadora(String transportadora);
    List<Logistica> findByStatusEntrega(String statusEntrega);
    void atualizarStatusEntrega(Long id, String novoStatus);
    void atualizarCodigoRastreio(Long id, String codigoRastreio);
} 