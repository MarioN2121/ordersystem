package com.innotech.ordersystem.service;

import com.innotech.ordersystem.model.Order;
import com.innotech.ordersystem.model.Stock;
import com.innotech.ordersystem.model.StockMovement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface StockMovementService {

    public List<StockMovement> listarTodos();
    public Optional<StockMovement> pesquisarPorId(Long id);
    public StockMovement criar(StockMovement stockMovement);
    public StockMovement atualizar(Long id, StockMovement stockMovement);
    public void remover(Long id);
    public List<StockMovement> pesquisarPorOrderId(Long orderId);
    public List<StockMovement> pesquisarPorStockId(Long stockId);
    public StockMovement atualizarStock(Long stockId, int quantidade);

}
