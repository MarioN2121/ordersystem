package com.innotech.ordersystem.repository;

import com.innotech.ordersystem.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    public List<Order> findByItemNameContaining(String nome);
}
