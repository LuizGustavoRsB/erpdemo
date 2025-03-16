package com.demoerp.erp.repository;

import com.demoerp.erp.model.Financeiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FinanceiroRepository extends JpaRepository<Financeiro, Long> {
    List<Financeiro> findByPedidosId(Long pedidoId);
    List<Financeiro> findByMarketplacesId(Long marketplaceId);
} 