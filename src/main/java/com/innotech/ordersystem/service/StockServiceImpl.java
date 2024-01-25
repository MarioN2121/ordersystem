package com.innotech.ordersystem.service;

import com.innotech.ordersystem.model.Item;
import com.innotech.ordersystem.model.Stock;
import com.innotech.ordersystem.repository.StockRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockServiceImpl implements StockService{

    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<Stock> listarTodos() {
        List<Stock> stockSalva = stockRepository.findAll();
        return stockSalva;
    }

    @Override
    public Optional<Stock> pesquisarPorId(Long id) {
        Optional<Stock> stockSalva = stockRepository.findById(id);
        return stockSalva;
    }

    @Override
    public Stock criar(Stock stock) {
        Stock itemSalva = stockRepository.save(stock);
        return itemSalva;
    }

    @Override
    public Stock atualizar(Long id, Stock stock) {
        Stock stockSalva = buscarStockPorId(id);
        stock.setId(id);
        BeanUtils.copyProperties(stock, stockSalva);
        return stockRepository.save(stockSalva);
    }

    @Override
    public void remover(Long id) {
        stockRepository.deleteById(id);
    }

    private Stock buscarStockPorId(Long id){
        Stock stockSalva = stockRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
        if(stockSalva == null){
            throw new EmptyResultDataAccessException(1);
        }
        return stockSalva;
    }

}
