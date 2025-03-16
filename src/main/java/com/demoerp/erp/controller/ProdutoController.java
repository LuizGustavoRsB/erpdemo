package com.demoerp.erp.controller;

import com.demoerp.erp.dto.ProdutoDTO;
import com.demoerp.erp.model.Produto;
import com.demoerp.erp.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<Produto> criar(@Valid @RequestBody ProdutoDTO produtoDTO) {
        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setDescricao(produtoDTO.getDescricao());
        produto.setCategoria(produtoDTO.getCategoria());
        produto.setPreco(produtoDTO.getPreco());
        produto.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());
        
        return new ResponseEntity<>(produtoService.save(produto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscarPorId(@PathVariable Long id) {
        return produtoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodos() {
        return ResponseEntity.ok(produtoService.findAll());
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Produto>> buscarPorCategoria(@PathVariable String categoria) {
        return ResponseEntity.ok(produtoService.findByCategoria(categoria));
    }

    @GetMapping("/estoque/{estoqueId}")
    public ResponseEntity<List<Produto>> buscarPorEstoque(@PathVariable Long estoqueId) {
        return ResponseEntity.ok(produtoService.findByEstoqueId(estoqueId));
    }

    @GetMapping("/busca")
    public ResponseEntity<List<Produto>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(produtoService.findByNomeContaining(nome));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoDTO produtoDTO) {
        return produtoService.findById(id)
                .map(produto -> {
                    produto.setNome(produtoDTO.getNome());
                    produto.setDescricao(produtoDTO.getDescricao());
                    produto.setCategoria(produtoDTO.getCategoria());
                    produto.setPreco(produtoDTO.getPreco());
                    produto.setQuantidadeEstoque(produtoDTO.getQuantidadeEstoque());
                    return ResponseEntity.ok(produtoService.save(produto));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/estoque")
    public ResponseEntity<Void> atualizarEstoque(@PathVariable Long id, @RequestParam Integer quantidade) {
        produtoService.atualizarEstoque(id, quantidade);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!produtoService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        produtoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{produtoId}/marketplaces/{marketplaceId}")
    public ResponseEntity<Void> vincularMarketplace(@PathVariable Long produtoId, @PathVariable Long marketplaceId) {
        produtoService.vincularMarketplace(produtoId, marketplaceId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{produtoId}/marketplaces/{marketplaceId}")
    public ResponseEntity<Void> desvincularMarketplace(@PathVariable Long produtoId, @PathVariable Long marketplaceId) {
        produtoService.desvincularMarketplace(produtoId, marketplaceId);
        return ResponseEntity.noContent().build();
    }
} 