package com.demoerp.erp.repository;

import com.demoerp.erp.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByCategoria(String categoria);
    List<Produto> findByEstoqueId(Long estoqueId);
    List<Produto> findByNomeContainingIgnoreCase(String nome);
} 