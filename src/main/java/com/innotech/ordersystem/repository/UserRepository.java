package com.innotech.ordersystem.repository;

import com.innotech.ordersystem.model.Item;
import com.innotech.ordersystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findByName(String name);
}
