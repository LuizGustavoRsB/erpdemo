package com.demoerp.erp.service;

import com.demoerp.erp.model.Loja;
import java.util.Optional;

public interface LojaService extends CrudService<Loja, Long> {
    Optional<Loja> findByCnpj(String cnpj);
    boolean existsByCnpj(String cnpj);
    void vincularMarketplace(Long lojaId, Long marketplaceId);
    void desvincularMarketplace(Long lojaId, Long marketplaceId);
} 