package com.demoerp.erp.service;

import com.demoerp.erp.model.Cliente;
import com.demoerp.erp.model.Pedido;
import java.util.List;
import java.util.Optional;

public interface ClienteService extends CrudService<Cliente, Long> {
    Optional<Cliente> findByCpfCnpj(String cpfCnpj);
    Optional<Cliente> findByEmail(String email);
    boolean existsByCpfCnpj(String cpfCnpj);
    List<Pedido> findPedidosByClienteId(Long clienteId);
} 