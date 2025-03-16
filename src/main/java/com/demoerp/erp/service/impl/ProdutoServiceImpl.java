package com.demoerp.erp.service.impl;

import com.demoerp.erp.model.Produto;
import com.demoerp.erp.model.Marketplace;
import com.demoerp.erp.repository.ProdutoRepository;
import com.demoerp.erp.repository.MarketplaceRepository;
import com.demoerp.erp.service.ProdutoService;
import com.demoerp.erp.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class ProdutoServiceImpl extends BaseCrudService<Produto, Long> implements ProdutoService {
    
    private final ProdutoRepository produtoRepository;
    private final MarketplaceRepository marketplaceRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository, MarketplaceRepository marketplaceRepository) {
        super(produtoRepository);
        this.produtoRepository = produtoRepository;
        this.marketplaceRepository = marketplaceRepository;
    }

    @Override
    public List<Produto> findByCategoria(String categoria) {
        return produtoRepository.findByCategoria(categoria);
    }

    @Override
    public List<Produto> findByEstoqueId(Long estoqueId) {
        return produtoRepository.findByEstoqueId(estoqueId);
    }

    @Override
    public List<Produto> findByNomeContaining(String nome) {
        return produtoRepository.findByNomeContainingIgnoreCase(nome);
    }

    @Override
    @Transactional
    public void atualizarEstoque(Long produtoId, Integer quantidade) {
        Produto produto = produtoRepository.findById(produtoId)
            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        if (quantidade < 0 && Math.abs(quantidade) > produto.getQuantidadeEstoque()) {
            throw new BusinessException("Quantidade insuficiente em estoque");
        }

        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + quantidade);
        produtoRepository.save(produto);
    }

    @Override
    @Transactional
    public void vincularMarketplace(Long produtoId, Long marketplaceId) {
        Produto produto = produtoRepository.findById(produtoId)
            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        
        Marketplace marketplace = marketplaceRepository.findById(marketplaceId)
            .orElseThrow(() -> new EntityNotFoundException("Marketplace não encontrado"));

        produto.getMarketplaces().add(marketplace);
        produtoRepository.save(produto);
    }

    @Override
    @Transactional
    public void desvincularMarketplace(Long produtoId, Long marketplaceId) {
        Produto produto = produtoRepository.findById(produtoId)
            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));
        
        produto.getMarketplaces().removeIf(m -> m.getId().equals(marketplaceId));
        produtoRepository.save(produto);
    }
} 