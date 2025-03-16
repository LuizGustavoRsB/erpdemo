package com.demoerp.erp.service.impl;

import com.demoerp.erp.model.Pedido;
import com.demoerp.erp.model.Produto;
import com.demoerp.erp.model.Logistica;
import com.demoerp.erp.model.Pedido.StatusPedido;
import com.demoerp.erp.repository.PedidoRepository;
import com.demoerp.erp.repository.ProdutoRepository;
import com.demoerp.erp.repository.LogisticaRepository;
import com.demoerp.erp.service.PedidoService;
import com.demoerp.erp.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class PedidoServiceImpl extends BaseCrudService<Pedido, Long> implements PedidoService {
    
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final LogisticaRepository logisticaRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository, 
                           ProdutoRepository produtoRepository,
                           LogisticaRepository logisticaRepository) {
        super(pedidoRepository);
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository;
        this.logisticaRepository = logisticaRepository;
    }

    @Override
    public List<Pedido> findByClienteId(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId);
    }

    @Override
    public List<Pedido> findByLojaId(Long lojaId) {
        return pedidoRepository.findByLojaId(lojaId);
    }

    @Override
    public List<Pedido> findByStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status);
    }

    @Override
    public List<Pedido> findByDataBetween(LocalDateTime inicio, LocalDateTime fim) {
        return pedidoRepository.findByDataBetween(inicio, fim);
    }

    @Override
    @Transactional
    public void atualizarStatus(Long pedidoId, StatusPedido novoStatus) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        // Validar a transição de status
        validarTransicaoStatus(pedido.getStatus(), novoStatus);

        pedido.setStatus(novoStatus);
        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void adicionarProduto(Long pedidoId, Long produtoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
            
        Produto produto = produtoRepository.findById(produtoId)
            .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

        if (produto.getQuantidadeEstoque() <= 0) {
            throw new BusinessException("Produto sem estoque disponível");
        }

        pedido.getProdutos().add(produto);
        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void removerProduto(Long pedidoId, Long produtoId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        pedido.getProdutos().removeIf(p -> p.getId().equals(produtoId));
        pedidoRepository.save(pedido);
    }

    @Override
    @Transactional
    public void vincularLogistica(Long pedidoId, Long logisticaId) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));
            
        Logistica logistica = logisticaRepository.findById(logisticaId)
            .orElseThrow(() -> new EntityNotFoundException("Logística não encontrada"));

        if (pedido.getStatus() != StatusPedido.PROCESSANDO) {
            throw new BusinessException("Pedido deve estar em processamento para vincular logística");
        }

        logistica.setPedido(pedido);
        logisticaRepository.save(logistica);
    }

    private void validarTransicaoStatus(StatusPedido statusAtual, StatusPedido novoStatus) {
        if (statusAtual == StatusPedido.ENTREGUE) {
            throw new BusinessException("Não é possível alterar o status de um pedido já entregue");
        }

        if (statusAtual == StatusPedido.PENDENTE && novoStatus == StatusPedido.ENVIADO) {
            throw new BusinessException("Pedido deve estar em processamento antes de ser enviado");
        }

        if (statusAtual == StatusPedido.PENDENTE && novoStatus == StatusPedido.ENTREGUE) {
            throw new BusinessException("Pedido deve estar enviado antes de ser entregue");
        }
    }
} 