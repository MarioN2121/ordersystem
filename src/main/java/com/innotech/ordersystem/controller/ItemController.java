package com.innotech.ordersystem.controller;


import com.innotech.ordersystem.model.Item;
import com.innotech.ordersystem.model.User;
import com.innotech.ordersystem.service.ItemServiceImpl;
import com.innotech.ordersystem.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemServiceImpl itemServiceImpl;


    @GetMapping("/listartodos")
    public List<Item> listar(){
        return itemServiceImpl.listarTodos();
    }

    @GetMapping("/pesquisar-por-id/{id}")
    public ResponseEntity<Optional<Item>> pesquisarPorId(@PathVariable Long id){
        Optional<Item> item = itemServiceImpl.pesquisarPorId(id);
        return item !=null ? ResponseEntity.ok(item) : ResponseEntity.notFound().build();
    }

    @GetMapping("/pesquisar-por-nome/{nome}")
    public ResponseEntity<List<Item>> pesquisarPorNome(@PathVariable String nome){
        List<Item> items = itemServiceImpl.pesquisarPorNome(nome);
        return items !=null ? ResponseEntity.ok(items) : ResponseEntity.notFound().build();
    }

    @PostMapping("/criar")
    public ResponseEntity<Item> criar(@RequestBody Item item){
        Item itemSalva = itemServiceImpl.criar(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(itemSalva);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Item> atualizar(@PathVariable Long id, @RequestBody Item item) {
        Item itemSalva = itemServiceImpl.atualizar(id, item);
        return ResponseEntity.ok(itemSalva);
    }

    @DeleteMapping("/remover/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        itemServiceImpl.remover(id);
    }


}
