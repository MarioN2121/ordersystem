package com.innotech.ordersystem.repository;

import com.innotech.ordersystem.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

   // public List<Item> findByName(String name);
    public List<Item> findByNameContaining(String name);

}
