package com.demoerp.erp.service.impl;

import com.demoerp.erp.model.Financeiro;
import com.demoerp.erp.model.Pedido;
import com.demoerp.erp.model.Marketplace;
import com.demoerp.erp.repository.FinanceiroRepository;
import com.demoerp.erp.repository.PedidoRepository;
import com.demoerp.erp.repository.MarketplaceRepository;
import com.demoerp.erp.service.FinanceiroService;
import com.demoerp.erp.exception.BusinessException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class FinanceiroServiceImpl extends BaseCrudService<Financeiro, Long> implements FinanceiroService {
    
    private final FinanceiroRepository financeiroRepository;
    private final PedidoRepository pedidoRepository;
    private final MarketplaceRepository marketplaceRepository;

    public FinanceiroServiceImpl(FinanceiroRepository financeiroRepository,
                               PedidoRepository pedidoRepository,
                               MarketplaceRepository marketplaceRepository) {
        super(financeiroRepository);
        this.financeiroRepository = financeiroRepository;
        this.pedidoRepository = pedidoRepository;
        this.marketplaceRepository = marketplaceRepository;
    }

    @Override
    public List<Financeiro> findByPedidosId(Long pedidoId) {
        return financeiroRepository.findByPedidosId(pedidoId);
    }

    @Override
    public List<Financeiro> findByMarketplacesId(Long marketplaceId) {
        return financeiroRepository.findByMarketplacesId(marketplaceId);
    }

    @Override
    @Transactional
    public void vincularPedido(Long financeiroId, Long pedidoId) {
        Financeiro financeiro = financeiroRepository.findById(financeiroId)
            .orElseThrow(() -> new EntityNotFoundException("Registro financeiro não encontrado"));
            
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        if (financeiro.getPedidos().contains(pedido)) {
            throw new BusinessException("Pedido já está vinculado a este registro financeiro");
        }

        financeiro.getPedidos().add(pedido);
        // Atualizar receitas com o valor do pedido
        financeiro.setReceitas(financeiro.getReceitas().add(pedido.getValorTotal()));
        financeiro.setLucro(calcularLucro(financeiroId));
        financeiroRepository.save(financeiro);
    }

    @Override
    @Transactional
    public void desvincularPedido(Long financeiroId, Long pedidoId) {
        Financeiro financeiro = financeiroRepository.findById(financeiroId)
            .orElseThrow(() -> new EntityNotFoundException("Registro financeiro não encontrado"));

        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        if (financeiro.getPedidos().remove(pedido)) {
            // Subtrair o valor do pedido das receitas
            financeiro.setReceitas(financeiro.getReceitas().subtract(pedido.getValorTotal()));
            financeiro.setLucro(calcularLucro(financeiroId));
            financeiroRepository.save(financeiro);
        }
    }

    @Override
    @Transactional
    public void vincularMarketplace(Long financeiroId, Long marketplaceId) {
        Financeiro financeiro = financeiroRepository.findById(financeiroId)
            .orElseThrow(() -> new EntityNotFoundException("Registro financeiro não encontrado"));
            
        Marketplace marketplace = marketplaceRepository.findById(marketplaceId)
            .orElseThrow(() -> new EntityNotFoundException("Marketplace não encontrado"));

        if (financeiro.getMarketplaces().contains(marketplace)) {
            throw new BusinessException("Marketplace já está vinculado a este registro financeiro");
        }

        financeiro.getMarketplaces().add(marketplace);
        // Atualizar taxas de marketplace
        financeiro.setTaxasMarketplace(financeiro.getTaxasMarketplace().add(marketplace.getTaxaComissao()));
        financeiro.setLucro(calcularLucro(financeiroId));
        financeiroRepository.save(financeiro);
    }

    @Override
    @Transactional
    public void desvincularMarketplace(Long financeiroId, Long marketplaceId) {
        Financeiro financeiro = financeiroRepository.findById(financeiroId)
            .orElseThrow(() -> new EntityNotFoundException("Registro financeiro não encontrado"));

        Marketplace marketplace = marketplaceRepository.findById(marketplaceId)
            .orElseThrow(() -> new EntityNotFoundException("Marketplace não encontrado"));

        if (financeiro.getMarketplaces().remove(marketplace)) {
            // Subtrair a taxa do marketplace
            financeiro.setTaxasMarketplace(financeiro.getTaxasMarketplace().subtract(marketplace.getTaxaComissao()));
            financeiro.setLucro(calcularLucro(financeiroId));
            financeiroRepository.save(financeiro);
        }
    }

    @Override
    @Transactional
    public void atualizarReceitas(Long id, BigDecimal novoValor) {
        if (novoValor.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("O valor das receitas não pode ser negativo");
        }

        Financeiro financeiro = financeiroRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Registro financeiro não encontrado"));

        financeiro.setReceitas(novoValor);
        financeiro.setLucro(calcularLucro(id));
        financeiroRepository.save(financeiro);
    }

    @Override
    @Transactional
    public void atualizarDespesas(Long id, BigDecimal novoValor) {
        if (novoValor.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("O valor das despesas não pode ser negativo");
        }

        Financeiro financeiro = financeiroRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Registro financeiro não encontrado"));

        financeiro.setDespesas(novoValor);
        financeiro.setLucro(calcularLucro(id));
        financeiroRepository.save(financeiro);
    }

    @Override
    @Transactional
    public void atualizarTaxasMarketplace(Long id, BigDecimal novoValor) {
        if (novoValor.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("O valor das taxas não pode ser negativo");
        }

        Financeiro financeiro = financeiroRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Registro financeiro não encontrado"));

        financeiro.setTaxasMarketplace(novoValor);
        financeiro.setLucro(calcularLucro(id));
        financeiroRepository.save(financeiro);
    }

    @Override
    @Transactional
    public void atualizarPagamentosPendentes(Long id, BigDecimal novoValor) {
        if (novoValor.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("O valor dos pagamentos pendentes não pode ser negativo");
        }

        Financeiro financeiro = financeiroRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Registro financeiro não encontrado"));

        financeiro.setPagamentosPendentes(novoValor);
        financeiroRepository.save(financeiro);
    }

    @Override
    public BigDecimal calcularLucro(Long id) {
        Financeiro financeiro = financeiroRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Registro financeiro não encontrado"));

        // Lucro = Receitas - (Despesas + Taxas Marketplace)
        return financeiro.getReceitas()
            .subtract(financeiro.getDespesas())
            .subtract(financeiro.getTaxasMarketplace());
    }

    @Override
    @Transactional
    public Financeiro save(Financeiro financeiro) {
        // Validar valores não negativos
        if (financeiro.getReceitas().compareTo(BigDecimal.ZERO) < 0 ||
            financeiro.getDespesas().compareTo(BigDecimal.ZERO) < 0 ||
            financeiro.getTaxasMarketplace().compareTo(BigDecimal.ZERO) < 0 ||
            financeiro.getPagamentosPendentes().compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Os valores financeiros não podem ser negativos");
        }

        // Calcular lucro antes de salvar
        financeiro.setLucro(financeiro.getReceitas()
            .subtract(financeiro.getDespesas())
            .subtract(financeiro.getTaxasMarketplace()));
        
        return super.save(financeiro);
    }
} 