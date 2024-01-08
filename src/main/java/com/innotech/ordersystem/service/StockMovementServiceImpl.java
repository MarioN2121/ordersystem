package com.innotech.ordersystem.service;

import com.innotech.ordersystem.model.Item;
import com.innotech.ordersystem.model.StockMovement;
import com.innotech.ordersystem.model.User;
import com.innotech.ordersystem.repository.StockMovementRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StockMovementServiceImpl implements StockMovementService{

    @Autowired
    private StockMovementRepository stockMovementRepository;

    @Override
    public List<StockMovement> listarTodos() {
        List<StockMovement> stockMovements = stockMovementRepository.findAll();
        return stockMovements;
    }

    @Override
    public Optional<StockMovement> pesquisarPorId(Long id) {
        Optional<StockMovement> stockMovement = stockMovementRepository.findById(id);
        return stockMovement;
    }

    @Override
    public List<StockMovement> pesquisarPorNome(String nome) {
        List<StockMovement> stockMovements = stockMovementRepository.findByItemNameContaining(nome);  //findByItemContaining(item);
        return stockMovements;
    }

    @Override
    public StockMovement criar(StockMovement stockMovement) {
        StockMovement stockMovementSalva = stockMovementRepository.save(stockMovement);
        return stockMovementSalva;
    }

    @Override
    public StockMovement atualizar(Long id, StockMovement stockMovement) {
        StockMovement stockMovementSalva = buscarItemPorId(id);
        if(stockMovementSalva!=null){
            stockMovement.setId(id);
            BeanUtils.copyProperties(stockMovement, stockMovementSalva);
            stockMovementSalva = stockMovementRepository.save(stockMovementSalva);
        }
        return stockMovementSalva;
    }

    @Override
    public void remover(Long id) {
        stockMovementRepository.deleteById(id);
    }

    //TODO ... ACRESCENTAR MAIS METODOS PARA AS OPERAÇOES DE ORDER(COMPRA)

    private StockMovement buscarItemPorId(Long id){
        StockMovement stockMovementSalva = stockMovementRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
        if(stockMovementSalva == null){
            throw new EmptyResultDataAccessException(1);
        }
        return stockMovementSalva;
    }
}
