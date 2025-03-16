package com.demoerp.erp.service.impl;

import com.demoerp.erp.model.Cliente;
import com.demoerp.erp.model.Pedido;
import com.demoerp.erp.repository.ClienteRepository;
import com.demoerp.erp.repository.PedidoRepository;
import com.demoerp.erp.service.ClienteService;
import com.demoerp.erp.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteServiceImpl extends BaseCrudService<Cliente, Long> implements ClienteService {
    
    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository, PedidoRepository pedidoRepository) {
        super(clienteRepository);
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public Optional<Cliente> findByCpfCnpj(String cpfCnpj) {
        return clienteRepository.findByCpfCnpj(cpfCnpj);
    }

    @Override
    public Optional<Cliente> findByEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    @Override
    public boolean existsByCpfCnpj(String cpfCnpj) {
        return clienteRepository.existsByCpfCnpj(cpfCnpj);
    }

    @Override
    public List<Pedido> findPedidosByClienteId(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) {
            throw new EntityNotFoundException("Cliente não encontrado");
        }
        return pedidoRepository.findByClienteId(clienteId);
    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        if (cliente.getId() == null && existsByCpfCnpj(cliente.getCpfCnpj())) {
            throw new BusinessException("CPF/CNPJ já cadastrado");
        }
        return super.save(cliente);
    }
} 