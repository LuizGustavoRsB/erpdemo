package com.demoerp.erp.repository;

import com.demoerp.erp.model.Pedido;
import com.demoerp.erp.model.Pedido.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByLojaId(Long lojaId);
    List<Pedido> findByStatus(StatusPedido status);
    List<Pedido> findByDataBetween(LocalDateTime inicio, LocalDateTime fim);
} 