package com.innotech.ordersystem.repository;

import com.innotech.ordersystem.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    public List<Item> findByNameContaining(String name);

}
