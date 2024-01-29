package com.innotech.ordersystem.repository;

import com.innotech.ordersystem.model.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    public List<StockMovement> findByOrderId(Long id);
    public List<StockMovement> findByStockId(Long id);

}
