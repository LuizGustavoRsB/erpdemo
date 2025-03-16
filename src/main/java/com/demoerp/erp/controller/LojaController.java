package com.demoerp.erp.controller;

import com.demoerp.erp.dto.LojaDTO;
import com.demoerp.erp.model.Loja;
import com.demoerp.erp.service.LojaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lojas")
public class LojaController {

    private final LojaService lojaService;

    public LojaController(LojaService lojaService) {
        this.lojaService = lojaService;
    }

    @PostMapping
    public ResponseEntity<Loja> criar(@Valid @RequestBody LojaDTO lojaDTO) {
        Loja loja = new Loja();
        loja.setNome(lojaDTO.getNome());
        loja.setCnpj(lojaDTO.getCnpj());
        loja.setEndereco(lojaDTO.getEndereco());
        loja.setTelefone(lojaDTO.getTelefone());
        loja.setEmail(lojaDTO.getEmail());
        
        return new ResponseEntity<>(lojaService.save(loja), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Loja> buscarPorId(@PathVariable Long id) {
        return lojaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Loja>> listarTodas() {
        return ResponseEntity.ok(lojaService.findAll());
    }

    @GetMapping("/cnpj/{cnpj}")
    public ResponseEntity<Loja> buscarPorCnpj(@PathVariable String cnpj) {
        return lojaService.findByCnpj(cnpj)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Loja> atualizar(@PathVariable Long id, @Valid @RequestBody LojaDTO lojaDTO) {
        return lojaService.findById(id)
                .map(loja -> {
                    loja.setNome(lojaDTO.getNome());
                    loja.setCnpj(lojaDTO.getCnpj());
                    loja.setEndereco(lojaDTO.getEndereco());
                    loja.setTelefone(lojaDTO.getTelefone());
                    loja.setEmail(lojaDTO.getEmail());
                    return ResponseEntity.ok(lojaService.save(loja));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!lojaService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        lojaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{lojaId}/marketplaces/{marketplaceId}")
    public ResponseEntity<Void> vincularMarketplace(@PathVariable Long lojaId, @PathVariable Long marketplaceId) {
        lojaService.vincularMarketplace(lojaId, marketplaceId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{lojaId}/marketplaces/{marketplaceId}")
    public ResponseEntity<Void> desvincularMarketplace(@PathVariable Long lojaId, @PathVariable Long marketplaceId) {
        lojaService.desvincularMarketplace(lojaId, marketplaceId);
        return ResponseEntity.noContent().build();
    }
} 