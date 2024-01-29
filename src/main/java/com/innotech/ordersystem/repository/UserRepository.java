package com.innotech.ordersystem.repository;

import com.innotech.ordersystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    public List<User> findByName(String name);
}
