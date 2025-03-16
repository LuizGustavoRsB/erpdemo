package com.demoerp.erp.service.impl;

import com.demoerp.erp.model.Logistica;
import com.demoerp.erp.model.Pedido;
import com.demoerp.erp.repository.LogisticaRepository;
import com.demoerp.erp.repository.PedidoRepository;
import com.demoerp.erp.service.LogisticaService;
import com.demoerp.erp.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LogisticaServiceImpl extends BaseCrudService<Logistica, Long> implements LogisticaService {
    
    private final LogisticaRepository logisticaRepository;
    private final PedidoRepository pedidoRepository;

    public LogisticaServiceImpl(LogisticaRepository logisticaRepository, PedidoRepository pedidoRepository) {
        super(logisticaRepository);
        this.logisticaRepository = logisticaRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public Optional<Logistica> findByPedidoId(Long pedidoId) {
        return logisticaRepository.findByPedidoId(pedidoId);
    }

    @Override
    public Optional<Logistica> findByCodigoRastreio(String codigoRastreio) {
        return logisticaRepository.findByCodigoRastreio(codigoRastreio);
    }

    @Override
    public List<Logistica> findByTransportadora(String transportadora) {
        return logisticaRepository.findByTransportadora(transportadora);
    }

    @Override
    public List<Logistica> findByStatusEntrega(String statusEntrega) {
        return logisticaRepository.findByStatusEntrega(statusEntrega);
    }

    @Override
    @Transactional
    public void atualizarStatusEntrega(Long id, String novoStatus) {
        Logistica logistica = logisticaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Logística não encontrada"));

        if (novoStatus == null || novoStatus.trim().isEmpty()) {
            throw new BusinessException("Status de entrega não pode ser vazio");
        }

        logistica.setStatusEntrega(novoStatus);
        logisticaRepository.save(logistica);

        // Se o status for de entrega concluída, atualizar o pedido
        if ("ENTREGUE".equalsIgnoreCase(novoStatus)) {
            Pedido pedido = logistica.getPedido();
            if (pedido != null) {
                pedido.setStatus(Pedido.StatusPedido.ENTREGUE);
                pedidoRepository.save(pedido);
            }
        }
    }

    @Override
    @Transactional
    public void atualizarCodigoRastreio(Long id, String codigoRastreio) {
        Logistica logistica = logisticaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Logística não encontrada"));

        if (codigoRastreio == null || codigoRastreio.trim().isEmpty()) {
            throw new BusinessException("Código de rastreio não pode ser vazio");
        }

        // Verificar se o código de rastreio já existe
        logisticaRepository.findByCodigoRastreio(codigoRastreio)
            .ifPresent(l -> {
                if (!l.getId().equals(id)) {
                    throw new BusinessException("Código de rastreio já existe");
                }
            });

        logistica.setCodigoRastreio(codigoRastreio);
        logisticaRepository.save(logistica);
    }

    @Override
    @Transactional
    public Logistica save(Logistica logistica) {
        if (logistica.getPedido() != null) {
            Pedido pedido = pedidoRepository.findById(logistica.getPedido().getId())
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
            
            // Verificar se já existe logística para este pedido
            if (logistica.getId() == null) {  // Novo registro
                logisticaRepository.findByPedidoId(pedido.getId())
                    .ifPresent(l -> {
                        throw new BusinessException("Já existe logística cadastrada para este pedido");
                    });
            }
            
            logistica.setPedido(pedido);
        }
        
        return super.save(logistica);
    }
} 