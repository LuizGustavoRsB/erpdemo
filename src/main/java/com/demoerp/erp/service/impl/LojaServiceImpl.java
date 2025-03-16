package com.demoerp.erp.service.impl;

import com.demoerp.erp.model.Loja;
import com.demoerp.erp.model.Marketplace;
import com.demoerp.erp.repository.LojaRepository;
import com.demoerp.erp.repository.MarketplaceRepository;
import com.demoerp.erp.service.LojaService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Service
@Transactional
public class LojaServiceImpl extends BaseCrudService<Loja, Long> implements LojaService {
    
    private final LojaRepository lojaRepository;
    private final MarketplaceRepository marketplaceRepository;

    public LojaServiceImpl(LojaRepository lojaRepository, MarketplaceRepository marketplaceRepository) {
        super(lojaRepository);
        this.lojaRepository = lojaRepository;
        this.marketplaceRepository = marketplaceRepository;
    }

    @Override
    public Optional<Loja> findByCnpj(String cnpj) {
        return lojaRepository.findByCnpj(cnpj);
    }

    @Override
    public boolean existsByCnpj(String cnpj) {
        return lojaRepository.existsByCnpj(cnpj);
    }

    @Override
    @Transactional
    public void vincularMarketplace(Long lojaId, Long marketplaceId) {
        Loja loja = lojaRepository.findById(lojaId)
            .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));
        
        Marketplace marketplace = marketplaceRepository.findById(marketplaceId)
            .orElseThrow(() -> new EntityNotFoundException("Marketplace não encontrado"));

        loja.getMarketplaces().add(marketplace);
        lojaRepository.save(loja);
    }

    @Override
    @Transactional
    public void desvincularMarketplace(Long lojaId, Long marketplaceId) {
        Loja loja = lojaRepository.findById(lojaId)
            .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));
        
        loja.getMarketplaces().removeIf(m -> m.getId().equals(marketplaceId));
        lojaRepository.save(loja);
    }
} 