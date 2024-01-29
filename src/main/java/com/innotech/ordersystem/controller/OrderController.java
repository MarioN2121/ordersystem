package com.innotech.ordersystem.controller;

import com.innotech.ordersystem.model.Order;
import com.innotech.ordersystem.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @GetMapping("/listartodos")
    public List<Order> listar(){
        return orderServiceImpl.listarTodos();
    }

    @GetMapping("/pesquisar-por-id/{id}")
    public ResponseEntity<Optional<Order>> pesquisarPorId(@PathVariable Long id){
        Optional<Order> order = orderServiceImpl.pesquisarPorId(id);
        return order !=null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
    }

    @GetMapping("/pesquisar-por-nome/{nome}")
    public ResponseEntity<List<Order>> pesquisarPorNome(@PathVariable String nome){
        List<Order> orders = orderServiceImpl.pesquisarPorNome(nome);
        return orders !=null ? ResponseEntity.ok(orders) : ResponseEntity.notFound().build();
    }

    @PostMapping("/criar")
    public ResponseEntity<Order> criar(@RequestBody Order order){
        Order orderSalva = orderServiceImpl.criar(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderSalva);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Order> atualizar(@PathVariable Long id, @RequestBody Order order) {
        Order orderSalva = orderServiceImpl.atualizar(id, order);
        return orderSalva !=null ? ResponseEntity.ok(orderSalva) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remover/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        orderServiceImpl.remover(id);
    }

}
