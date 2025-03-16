package com.demoerp.erp.controller;

import com.demoerp.erp.dto.MarketplaceDTO;
import com.demoerp.erp.model.Marketplace;
import com.demoerp.erp.service.MarketplaceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/marketplaces")
public class MarketplaceController {

    private final MarketplaceService marketplaceService;

    public MarketplaceController(MarketplaceService marketplaceService) {
        this.marketplaceService = marketplaceService;
    }

    @PostMapping
    public ResponseEntity<Marketplace> criar(@Valid @RequestBody MarketplaceDTO marketplaceDTO) {
        Marketplace marketplace = new Marketplace();
        marketplace.setNome(marketplaceDTO.getNome());
        marketplace.setTaxaComissao(marketplaceDTO.getTaxaComissao());
        marketplace.setPoliticasEnvio(marketplaceDTO.getPoliticasEnvio());
        
        return new ResponseEntity<>(marketplaceService.save(marketplace), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marketplace> buscarPorId(@PathVariable Long id) {
        return marketplaceService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Marketplace>> listarTodos() {
        return ResponseEntity.ok(marketplaceService.findAll());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Marketplace> buscarPorNome(@PathVariable String nome) {
        return marketplaceService.findByNome(nome)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/loja/{lojaId}")
    public ResponseEntity<List<Marketplace>> buscarPorLoja(@PathVariable Long lojaId) {
        return ResponseEntity.ok(marketplaceService.findByLojasId(lojaId));
    }

    @GetMapping("/produto/{produtoId}")
    public ResponseEntity<List<Marketplace>> buscarPorProduto(@PathVariable Long produtoId) {
        return ResponseEntity.ok(marketplaceService.findByProdutosId(produtoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marketplace> atualizar(@PathVariable Long id, @Valid @RequestBody MarketplaceDTO marketplaceDTO) {
        return marketplaceService.findById(id)
                .map(marketplace -> {
                    marketplace.setNome(marketplaceDTO.getNome());
                    marketplace.setTaxaComissao(marketplaceDTO.getTaxaComissao());
                    marketplace.setPoliticasEnvio(marketplaceDTO.getPoliticasEnvio());
                    return ResponseEntity.ok(marketplaceService.save(marketplace));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{marketplaceId}/lojas/{lojaId}")
    public ResponseEntity<Void> vincularLoja(@PathVariable Long marketplaceId, @PathVariable Long lojaId) {
        marketplaceService.vincularLoja(marketplaceId, lojaId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{marketplaceId}/lojas/{lojaId}")
    public ResponseEntity<Void> desvincularLoja(@PathVariable Long marketplaceId, @PathVariable Long lojaId) {
        marketplaceService.desvincularLoja(marketplaceId, lojaId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{marketplaceId}/produtos/{produtoId}")
    public ResponseEntity<Void> vincularProduto(@PathVariable Long marketplaceId, @PathVariable Long produtoId) {
        marketplaceService.vincularProduto(marketplaceId, produtoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{marketplaceId}/produtos/{produtoId}")
    public ResponseEntity<Void> desvincularProduto(@PathVariable Long marketplaceId, @PathVariable Long produtoId) {
        marketplaceService.desvincularProduto(marketplaceId, produtoId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{marketplaceId}/estoques/{estoqueId}")
    public ResponseEntity<Void> vincularEstoque(@PathVariable Long marketplaceId, @PathVariable Long estoqueId) {
        marketplaceService.vincularEstoque(marketplaceId, estoqueId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{marketplaceId}/estoques/{estoqueId}")
    public ResponseEntity<Void> desvincularEstoque(@PathVariable Long marketplaceId, @PathVariable Long estoqueId) {
        marketplaceService.desvincularEstoque(marketplaceId, estoqueId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!marketplaceService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        marketplaceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 