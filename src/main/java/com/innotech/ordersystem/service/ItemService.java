package com.innotech.ordersystem.service;

import com.innotech.ordersystem.model.Item;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ItemService {

    public List<Item> listarTodos();
    public Optional<Item> pesquisarPorId(Long id);
    public List<Item> pesquisarPorNome(String nome);
    public Item criar(Item item);
    public Item atualizar(Long id, Item item);
    public void remover(Long id);

}
