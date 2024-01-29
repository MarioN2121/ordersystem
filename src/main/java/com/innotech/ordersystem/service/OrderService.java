package com.innotech.ordersystem.service;

import com.innotech.ordersystem.model.EmailSend;
import com.innotech.ordersystem.model.Order;
import com.innotech.ordersystem.model.Stock;
import com.innotech.ordersystem.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {


    public List<Order> listarTodos();
    public Optional<Order> pesquisarPorId(Long id);
    public List<Order> pesquisarPorNome(String nome);
    public Order criar(Order order);
    public Order atualizar(Long id, Order order);
    public void remover(Long id);


}
