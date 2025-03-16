package com.demoerp.erp.controller;

import com.demoerp.erp.dto.FinanceiroDTO;
import com.demoerp.erp.model.Financeiro;
import com.demoerp.erp.service.FinanceiroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/financeiro")
public class FinanceiroController {

    private final FinanceiroService financeiroService;

    public FinanceiroController(FinanceiroService financeiroService) {
        this.financeiroService = financeiroService;
    }

    @PostMapping
    public ResponseEntity<FinanceiroDTO> criar(@Valid @RequestBody FinanceiroDTO financeiroDTO) {
        Financeiro financeiro = convertToEntity(financeiroDTO);
        Financeiro savedFinanceiro = financeiroService.save(financeiro);
        return new ResponseEntity<>(convertToDTO(savedFinanceiro), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinanceiroDTO> buscarPorId(@PathVariable Long id) {
        return financeiroService.findById(id)
            .map(financeiro -> ResponseEntity.ok(convertToDTO(financeiro)))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<FinanceiroDTO>> listarTodos() {
        List<FinanceiroDTO> financeiros = financeiroService.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(financeiros);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinanceiroDTO> atualizar(@PathVariable Long id, @Valid @RequestBody FinanceiroDTO financeiroDTO) {
        if (!financeiroService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        Financeiro financeiro = convertToEntity(financeiroDTO);
        financeiro.setId(id);
        return ResponseEntity.ok(convertToDTO(financeiroService.save(financeiro)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!financeiroService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        
        financeiroService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<List<FinanceiroDTO>> buscarPorPedidoId(@PathVariable Long pedidoId) {
        List<FinanceiroDTO> financeiros = financeiroService.findByPedidosId(pedidoId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(financeiros);
    }

    @GetMapping("/marketplace/{marketplaceId}")
    public ResponseEntity<List<FinanceiroDTO>> buscarPorMarketplaceId(@PathVariable Long marketplaceId) {
        List<FinanceiroDTO> financeiros = financeiroService.findByMarketplacesId(marketplaceId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(financeiros);
    }

    @PostMapping("/{financeiroId}/pedido/{pedidoId}")
    public ResponseEntity<Void> vincularPedido(@PathVariable Long financeiroId, @PathVariable Long pedidoId) {
        financeiroService.vincularPedido(financeiroId, pedidoId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{financeiroId}/pedido/{pedidoId}")
    public ResponseEntity<Void> desvincularPedido(@PathVariable Long financeiroId, @PathVariable Long pedidoId) {
        financeiroService.desvincularPedido(financeiroId, pedidoId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{financeiroId}/marketplace/{marketplaceId}")
    public ResponseEntity<Void> vincularMarketplace(@PathVariable Long financeiroId, @PathVariable Long marketplaceId) {
        financeiroService.vincularMarketplace(financeiroId, marketplaceId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{financeiroId}/marketplace/{marketplaceId}")
    public ResponseEntity<Void> desvincularMarketplace(@PathVariable Long financeiroId, @PathVariable Long marketplaceId) {
        financeiroService.desvincularMarketplace(financeiroId, marketplaceId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/receitas")
    public ResponseEntity<Void> atualizarReceitas(@PathVariable Long id, @RequestBody BigDecimal novoValor) {
        financeiroService.atualizarReceitas(id, novoValor);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/despesas")
    public ResponseEntity<Void> atualizarDespesas(@PathVariable Long id, @RequestBody BigDecimal novoValor) {
        financeiroService.atualizarDespesas(id, novoValor);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/taxas-marketplace")
    public ResponseEntity<Void> atualizarTaxasMarketplace(@PathVariable Long id, @RequestBody BigDecimal novoValor) {
        financeiroService.atualizarTaxasMarketplace(id, novoValor);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/pagamentos-pendentes")
    public ResponseEntity<Void> atualizarPagamentosPendentes(@PathVariable Long id, @RequestBody BigDecimal novoValor) {
        financeiroService.atualizarPagamentosPendentes(id, novoValor);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/lucro")
    public ResponseEntity<BigDecimal> calcularLucro(@PathVariable Long id) {
        return ResponseEntity.ok(financeiroService.calcularLucro(id));
    }

    private Financeiro convertToEntity(FinanceiroDTO dto) {
        Financeiro financeiro = new Financeiro();
        financeiro.setId(dto.getId());
        financeiro.setReceitas(dto.getReceitas());
        financeiro.setDespesas(dto.getDespesas());
        financeiro.setLucro(dto.getLucro());
        financeiro.setTaxasMarketplace(dto.getTaxasMarketplace());
        financeiro.setPagamentosPendentes(dto.getPagamentosPendentes());
        return financeiro;
    }

    private FinanceiroDTO convertToDTO(Financeiro entity) {
        FinanceiroDTO dto = new FinanceiroDTO();
        dto.setId(entity.getId());
        dto.setReceitas(entity.getReceitas());
        dto.setDespesas(entity.getDespesas());
        dto.setLucro(entity.getLucro());
        dto.setTaxasMarketplace(entity.getTaxasMarketplace());
        dto.setPagamentosPendentes(entity.getPagamentosPendentes());
        
        if (entity.getPedidos() != null) {
            dto.setPedidoIds(entity.getPedidos().stream()
                .map(pedido -> pedido.getId())
                .collect(Collectors.toList()));
        }
        
        if (entity.getMarketplaces() != null) {
            dto.setMarketplaceIds(entity.getMarketplaces().stream()
                .map(marketplace -> marketplace.getId())
                .collect(Collectors.toList()));
        }
        
        return dto;
    }
} 