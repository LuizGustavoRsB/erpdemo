package com.demoerp.erp.repository;

import com.demoerp.erp.model.Estoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
    Optional<Estoque> findByLojaId(Long lojaId);
    List<Estoque> findByLocalizacaoContainingIgnoreCase(String localizacao);
} 