package com.demoerp.erp.service.impl;

import com.demoerp.erp.model.Marketplace;
import com.demoerp.erp.model.Loja;
import com.demoerp.erp.model.Produto;
import com.demoerp.erp.model.Estoque;
import com.demoerp.erp.repository.MarketplaceRepository;
import com.demoerp.erp.repository.LojaRepository;
import com.demoerp.erp.repository.ProdutoRepository;
import com.demoerp.erp.repository.EstoqueRepository;
import com.demoerp.erp.service.MarketplaceService;
import com.demoerp.erp.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MarketplaceServiceImpl extends BaseCrudService<Marketplace, Long> implements MarketplaceService {
    
    private final MarketplaceRepository marketplaceRepository;
    private final LojaRepository lojaRepository;
    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;

    public MarketplaceServiceImpl(MarketplaceRepository marketplaceRepository,
                                 LojaRepository lojaRepository,
                                 ProdutoRepository produtoRepository,
                                 EstoqueRepository estoqueRepository) {
        super(marketplaceRepository);
        this.marketplaceRepository = marketplaceRepository;
        this.lojaRepository = lojaRepository;
        this.produtoRepository = produtoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    @Override
    public Optional<Marketplace> findByNome(String nome) {
        return marketplaceRepository.findByNome(nome);
    }

    @Override
    public List<Marketplace> findByLojasId(Long lojaId) {
        return marketplaceRepository.findByLojasId(lojaId);
    }

    @Override
    public List<Marketplace> findByProdutosId(Long produtoId) {
        return marketplaceRepository.findByProdutosId(produtoId);
    }

    @Override
    @Transactional
    public void vincularLoja(Long marketplaceId, Long lojaId) {
        Marketplace marketplace = findById(marketplaceId)
            .orElseThrow(() -> new EntityNotFoundException("Marketplace não encontrado"));
            
        Loja loja = lojaRepository.findById(lojaId)
            .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));

        if (marketplace.getLojas().contains(loja)) {
            throw new BusinessException("Loja já está vinculada a este marketplace");
        }

        marketplace.getLojas().add(loja);
        save(marketplace);
    }

    @Override
    @Transactional
    public void desvincularLoja(Long marketplaceId, Long lojaId) {
        Marketplace marketplace = findById(marketplaceId)
            .orElseThrow(() -> new EntityNotFoundException("Marketplace não encontrado"));
            
        Loja loja = lojaRepository.findById(lojaId)
            .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));

        if (marketplace.getLojas().remove(loja)) {
            save(marketplace);
        }
    }

    @Override
    @Transactional
    public void vincularProduto(Long marketplaceId, Long produtoId) {
        Marketplace marketplace = findById(marketplaceId)
            .orElseThrow(() -> new EntityNotFoundException("Marketplace não encontrado"));
            
        Produto produto = produtoRepository.findById(produtoId)
            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        if (marketplace.getProdutos().contains(produto)) {
            throw new BusinessException("Produto já está vinculado a este marketplace");
        }

        marketplace.getProdutos().add(produto);
        save(marketplace);
    }

    @Override
    @Transactional
    public void desvincularProduto(Long marketplaceId, Long produtoId) {
        Marketplace marketplace = findById(marketplaceId)
            .orElseThrow(() -> new EntityNotFoundException("Marketplace não encontrado"));
            
        Produto produto = produtoRepository.findById(produtoId)
            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        if (marketplace.getProdutos().remove(produto)) {
            save(marketplace);
        }
    }

    @Override
    @Transactional
    public void vincularEstoque(Long marketplaceId, Long estoqueId) {
        Marketplace marketplace = findById(marketplaceId)
            .orElseThrow(() -> new EntityNotFoundException("Marketplace não encontrado"));
            
        Estoque estoque = estoqueRepository.findById(estoqueId)
            .orElseThrow(() -> new EntityNotFoundException("Estoque não encontrado"));

        if (marketplace.getEstoques().contains(estoque)) {
            throw new BusinessException("Estoque já está vinculado a este marketplace");
        }

        marketplace.getEstoques().add(estoque);
        save(marketplace);
    }

    @Override
    @Transactional
    public void desvincularEstoque(Long marketplaceId, Long estoqueId) {
        Marketplace marketplace = findById(marketplaceId)
            .orElseThrow(() -> new EntityNotFoundException("Marketplace não encontrado"));
            
        Estoque estoque = estoqueRepository.findById(estoqueId)
            .orElseThrow(() -> new EntityNotFoundException("Estoque não encontrado"));

        if (marketplace.getEstoques().remove(estoque)) {
            save(marketplace);
        }
    }

    @Override
    @Transactional
    public Marketplace save(Marketplace marketplace) {
        // Validar nome único
        Optional<Marketplace> existingMarketplace = marketplaceRepository.findByNome(marketplace.getNome());
        if (existingMarketplace.isPresent() && !existingMarketplace.get().getId().equals(marketplace.getId())) {
            throw new BusinessException("Já existe um marketplace com este nome");
        }
        
        return super.save(marketplace);
    }
} 