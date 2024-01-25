package com.innotech.ordersystem.repository;

import com.innotech.ordersystem.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {

}
