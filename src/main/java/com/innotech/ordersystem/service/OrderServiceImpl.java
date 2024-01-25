package com.innotech.ordersystem.service;

import com.innotech.ordersystem.model.Order;
import com.innotech.ordersystem.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;


    @Override
    public List<Order> listarTodos() {
        List<Order> orders = orderRepository.findAll();
        return orders;
    }

    @Override
    public Optional<Order> pesquisarPorId(Long id) {
        Optional<Order> orders = orderRepository.findById(id);
        return orders;
    }

    @Override
    public List<Order> pesquisarPorNome(String nome) {
        List<Order> orders = orderRepository.findByItemNameContaining(nome);  //findByItemContaining(item);
        return orders;
    }

    @Override
    public Order criar(Order order) {
        //TODO ... - when an order is created, it should try to satisfy it with the current stock.;
        // se a Order for superior ao stock entao faz subtração e fica em negativo
        Order orderSalva = orderRepository.save(order);
        return orderSalva;
    }

    @Override
    public Order atualizar(Long id, Order order) {
        Order orderSalva = buscarItemPorId(id);
        if(orderSalva!=null){
            order.setId(id);
            BeanUtils.copyProperties(order, orderSalva);
            orderSalva = orderRepository.save(orderSalva);
        }
        return orderSalva;
    }

    @Override
    public void remover(Long id) {
        orderRepository.deleteById(id);
    }

    private Order buscarItemPorId(Long id){
        Order orderSalva = orderRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
        if(orderSalva == null){
            throw new EmptyResultDataAccessException(1);
        }
        return orderSalva;
    }


}
