package com.demoerp.erp.service;

import com.demoerp.erp.model.Produto;
import java.util.List;

public interface ProdutoService extends CrudService<Produto, Long> {
    List<Produto> findByCategoria(String categoria);
    List<Produto> findByEstoqueId(Long estoqueId);
    List<Produto> findByNomeContaining(String nome);
    void atualizarEstoque(Long produtoId, Integer quantidade);
    void vincularMarketplace(Long produtoId, Long marketplaceId);
    void desvincularMarketplace(Long produtoId, Long marketplaceId);
} 