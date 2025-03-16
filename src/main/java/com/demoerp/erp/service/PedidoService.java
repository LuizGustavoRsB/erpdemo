package com.demoerp.erp.service;

import com.demoerp.erp.model.Pedido;
import com.demoerp.erp.model.Pedido.StatusPedido;
import java.time.LocalDateTime;
import java.util.List;

public interface PedidoService extends CrudService<Pedido, Long> {
    List<Pedido> findByClienteId(Long clienteId);
    List<Pedido> findByLojaId(Long lojaId);
    List<Pedido> findByStatus(StatusPedido status);
    List<Pedido> findByDataBetween(LocalDateTime inicio, LocalDateTime fim);
    void atualizarStatus(Long pedidoId, StatusPedido novoStatus);
    void adicionarProduto(Long pedidoId, Long produtoId);
    void removerProduto(Long pedidoId, Long produtoId);
    void vincularLogistica(Long pedidoId, Long logisticaId);
} 