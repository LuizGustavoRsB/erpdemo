package com.demoerp.erp.service;

import com.demoerp.erp.model.Marketplace;
import java.util.List;
import java.util.Optional;

public interface MarketplaceService extends CrudService<Marketplace, Long> {
    Optional<Marketplace> findByNome(String nome);
    List<Marketplace> findByLojasId(Long lojaId);
    List<Marketplace> findByProdutosId(Long produtoId);
    void vincularLoja(Long marketplaceId, Long lojaId);
    void desvincularLoja(Long marketplaceId, Long lojaId);
    void vincularProduto(Long marketplaceId, Long produtoId);
    void desvincularProduto(Long marketplaceId, Long produtoId);
    void vincularEstoque(Long marketplaceId, Long estoqueId);
    void desvincularEstoque(Long marketplaceId, Long estoqueId);
} 