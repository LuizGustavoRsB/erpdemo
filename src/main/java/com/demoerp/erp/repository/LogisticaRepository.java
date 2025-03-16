package com.demoerp.erp.repository;

import com.demoerp.erp.model.Logistica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface LogisticaRepository extends JpaRepository<Logistica, Long> {
    Optional<Logistica> findByPedidoId(Long pedidoId);
    List<Logistica> findByTransportadora(String transportadora);
    Optional<Logistica> findByCodigoRastreio(String codigoRastreio);
    List<Logistica> findByStatusEntrega(String statusEntrega);
} 