package com.demoerp.erp.service;

import com.demoerp.erp.model.Financeiro;
import java.math.BigDecimal;
import java.util.List;

public interface FinanceiroService extends CrudService<Financeiro, Long> {
    List<Financeiro> findByPedidosId(Long pedidoId);
    List<Financeiro> findByMarketplacesId(Long marketplaceId);
    void vincularPedido(Long financeiroId, Long pedidoId);
    void desvincularPedido(Long financeiroId, Long pedidoId);
    void vincularMarketplace(Long financeiroId, Long marketplaceId);
    void desvincularMarketplace(Long financeiroId, Long marketplaceId);
    void atualizarReceitas(Long id, BigDecimal novoValor);
    void atualizarDespesas(Long id, BigDecimal novoValor);
    void atualizarTaxasMarketplace(Long id, BigDecimal novoValor);
    void atualizarPagamentosPendentes(Long id, BigDecimal novoValor);
    BigDecimal calcularLucro(Long id);
} 