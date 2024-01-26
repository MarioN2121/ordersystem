package com.innotech.ordersystem.service;

import com.innotech.ordersystem.model.Stock;
import com.innotech.ordersystem.model.StockMovement;
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
        List<StockMovement> stockMovement = stockMovementRepository.findAll();
        return stockMovement;
    }

    @Override
    public Optional<StockMovement> pesquisarPorId(Long id) {
        Optional<StockMovement> stockMovement = stockMovementRepository.findById(id);
        return stockMovement;
    }


    @Override
    public StockMovement criar(StockMovement stockMovement) {
        //TODO ... - when a stock movement is created, the system should try to attribute it to an order
        // // that isn't complete;
        // o Stock verifica as Order em negativo e atualiza

        //TODO ... - when an order is complete, send a notification by email to the user that created it;
        // Verifica se a Order está completa e envia a notificação ao user
        //TODO ... OBS: simular com uma mensagem log "Email enviado!"
        //StockMovement stockSalva = stockMovementRepository.save(stock);
        //return stockSalva;
        return null;
    }

    @Override
    public StockMovement atualizar(Long id, StockMovement stockMovement) {
        StockMovement stockMovementSalva = buscarItemPorId(id);
        if(stockMovementSalva !=null){
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
        StockMovement stockSalva = stockMovementRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
        if(stockSalva == null){
            throw new EmptyResultDataAccessException(1);
        }
        return stockSalva;
    }
}
