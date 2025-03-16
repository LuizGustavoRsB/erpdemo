package com.demoerp.erp.controller;

import com.demoerp.erp.dto.LogisticaDTO;
import com.demoerp.erp.model.Logistica;
import com.demoerp.erp.model.Pedido;
import com.demoerp.erp.service.LogisticaService;
import com.demoerp.erp.service.PedidoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/logistica")
public class LogisticaController {

    private final LogisticaService logisticaService;
    private final PedidoService pedidoService;

    public LogisticaController(LogisticaService logisticaService, PedidoService pedidoService) {
        this.logisticaService = logisticaService;
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public ResponseEntity<Logistica> criar(@Valid @RequestBody LogisticaDTO logisticaDTO) {
        Pedido pedido = pedidoService.findById(logisticaDTO.getPedidoId())
            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Pedido não encontrado"));

        Logistica logistica = new Logistica();
        logistica.setMetodoEnvio(logisticaDTO.getMetodoEnvio());
        logistica.setTransportadora(logisticaDTO.getTransportadora());
        logistica.setCodigoRastreio(logisticaDTO.getCodigoRastreio());
        logistica.setStatusEntrega(logisticaDTO.getStatusEntrega());
        logistica.setPedido(pedido);
        
        return new ResponseEntity<>(logisticaService.save(logistica), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Logistica> buscarPorId(@PathVariable Long id) {
        return logisticaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Logistica>> listarTodos() {
        return ResponseEntity.ok(logisticaService.findAll());
    }

    @GetMapping("/pedido/{pedidoId}")
    public ResponseEntity<Logistica> buscarPorPedido(@PathVariable Long pedidoId) {
        return logisticaService.findByPedidoId(pedidoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/rastreio/{codigoRastreio}")
    public ResponseEntity<Logistica> buscarPorCodigoRastreio(@PathVariable String codigoRastreio) {
        return logisticaService.findByCodigoRastreio(codigoRastreio)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/transportadora/{transportadora}")
    public ResponseEntity<List<Logistica>> buscarPorTransportadora(@PathVariable String transportadora) {
        return ResponseEntity.ok(logisticaService.findByTransportadora(transportadora));
    }

    @GetMapping("/status/{statusEntrega}")
    public ResponseEntity<List<Logistica>> buscarPorStatusEntrega(@PathVariable String statusEntrega) {
        return ResponseEntity.ok(logisticaService.findByStatusEntrega(statusEntrega));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> atualizarStatusEntrega(@PathVariable Long id, @RequestParam String status) {
        logisticaService.atualizarStatusEntrega(id, status);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/rastreio")
    public ResponseEntity<Void> atualizarCodigoRastreio(@PathVariable Long id, @RequestParam String codigoRastreio) {
        logisticaService.atualizarCodigoRastreio(id, codigoRastreio);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Logistica> atualizar(@PathVariable Long id, @Valid @RequestBody LogisticaDTO logisticaDTO) {
        return logisticaService.findById(id)
                .map(logistica -> {
                    logistica.setMetodoEnvio(logisticaDTO.getMetodoEnvio());
                    logistica.setTransportadora(logisticaDTO.getTransportadora());
                    logistica.setCodigoRastreio(logisticaDTO.getCodigoRastreio());
                    logistica.setStatusEntrega(logisticaDTO.getStatusEntrega());
                    
                    if (logisticaDTO.getPedidoId() != null) {
                        Pedido pedido = pedidoService.findById(logisticaDTO.getPedidoId())
                            .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Pedido não encontrado"));
                        logistica.setPedido(pedido);
                    }
                    
                    return ResponseEntity.ok(logisticaService.save(logistica));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!logisticaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        logisticaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 