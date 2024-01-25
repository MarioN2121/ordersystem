package com.innotech.ordersystem.service;

import com.innotech.ordersystem.model.Stock;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface StockService {

    public List<Stock> listarTodos();
    public Optional<Stock> pesquisarPorId(Long id);
    public Stock criar(Stock stock);
    public Stock atualizar(Long id, Stock stock);
    public void remover(Long id);

}
