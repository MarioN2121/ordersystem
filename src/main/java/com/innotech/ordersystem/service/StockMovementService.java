package com.innotech.ordersystem.service;

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
    //TODO ... ACRESCENTAR MAIS METODOS PARA AS OPERAÇOES DE ORDER(COMPRA)
}
