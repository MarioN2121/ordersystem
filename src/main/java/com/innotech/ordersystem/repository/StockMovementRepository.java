package com.innotech.ordersystem.repository;

import com.innotech.ordersystem.model.Item;
import com.innotech.ordersystem.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {

    public List<StockMovement> findByItem(Item item);
    public List<StockMovement> findByItemNameContaining(String nome);
}
