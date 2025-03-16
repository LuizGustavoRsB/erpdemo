package com.demoerp.erp.controller;

import com.demoerp.erp.dto.PedidoDTO;
import com.demoerp.erp.model.Pedido;
import com.demoerp.erp.model.Pedido.StatusPedido;
import com.demoerp.erp.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Pedido> criar(@Valid @RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setData(LocalDateTime.now());
        pedido.setValorTotal(pedidoDTO.getValorTotal());
        pedido.setStatus(pedidoDTO.getStatus());
        
        return new ResponseEntity<>(pedidoService.save(pedido), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        return pedidoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        return ResponseEntity.ok(pedidoService.findAll());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Pedido>> buscarPorCliente(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pedidoService.findByClienteId(clienteId));
    }

    @GetMapping("/loja/{lojaId}")
    public ResponseEntity<List<Pedido>> buscarPorLoja(@PathVariable Long lojaId) {
        return ResponseEntity.ok(pedidoService.findByLojaId(lojaId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Pedido>> buscarPorStatus(@PathVariable StatusPedido status) {
        return ResponseEntity.ok(pedidoService.findByStatus(status));
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<Pedido>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fim) {
        return ResponseEntity.ok(pedidoService.findByDataBetween(inicio, fim));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatus(@PathVariable Long id, @RequestParam StatusPedido status) {
        pedidoService.atualizarStatus(id, status);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{pedidoId}/produtos/{produtoId}")
    public ResponseEntity<Void> adicionarProduto(@PathVariable Long pedidoId, @PathVariable Long produtoId) {
        pedidoService.adicionarProduto(pedidoId, produtoId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{pedidoId}/produtos/{produtoId}")
    public ResponseEntity<Void> removerProduto(@PathVariable Long pedidoId, @PathVariable Long produtoId) {
        pedidoService.removerProduto(pedidoId, produtoId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{pedidoId}/logistica/{logisticaId}")
    public ResponseEntity<Void> vincularLogistica(@PathVariable Long pedidoId, @PathVariable Long logisticaId) {
        pedidoService.vincularLogistica(pedidoId, logisticaId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!pedidoService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        pedidoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 