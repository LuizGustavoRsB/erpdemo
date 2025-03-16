package com.demoerp.erp.repository;

import com.demoerp.erp.model.Marketplace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MarketplaceRepository extends JpaRepository<Marketplace, Long> {
    Optional<Marketplace> findByNome(String nome);
    List<Marketplace> findByLojasId(Long lojaId);
    List<Marketplace> findByProdutosId(Long produtoId);
} 