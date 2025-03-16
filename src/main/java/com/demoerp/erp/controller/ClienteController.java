package com.demoerp.erp.controller;

import com.demoerp.erp.dto.ClienteDTO;
import com.demoerp.erp.model.Cliente;
import com.demoerp.erp.model.Pedido;
import com.demoerp.erp.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Cliente> criar(@Valid @RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.getNome());
        cliente.setCpfCnpj(clienteDTO.getCpfCnpj());
        cliente.setEndereco(clienteDTO.getEndereco());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setEmail(clienteDTO.getEmail());
        
        return new ResponseEntity<>(clienteService.save(cliente), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarPorId(@PathVariable Long id) {
        return clienteService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarTodos() {
        return ResponseEntity.ok(clienteService.findAll());
    }

    @GetMapping("/cpf-cnpj/{cpfCnpj}")
    public ResponseEntity<Cliente> buscarPorCpfCnpj(@PathVariable String cpfCnpj) {
        return clienteService.findByCpfCnpj(cpfCnpj)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Cliente> buscarPorEmail(@PathVariable String email) {
        return clienteService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/pedidos")
    public ResponseEntity<List<Pedido>> listarPedidos(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.findPedidosByClienteId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> atualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO clienteDTO) {
        return clienteService.findById(id)
                .map(cliente -> {
                    cliente.setNome(clienteDTO.getNome());
                    cliente.setCpfCnpj(clienteDTO.getCpfCnpj());
                    cliente.setEndereco(clienteDTO.getEndereco());
                    cliente.setTelefone(clienteDTO.getTelefone());
                    cliente.setEmail(clienteDTO.getEmail());
                    return ResponseEntity.ok(clienteService.save(cliente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!clienteService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
} 