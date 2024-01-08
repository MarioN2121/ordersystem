package com.innotech.ordersystem.service;

import com.innotech.ordersystem.model.Item;
import com.innotech.ordersystem.repository.ItemRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> listarTodos() {
        List<Item> areaSaudes = itemRepository.findAll();
        return areaSaudes;
    }

    @Override
    public Optional<Item> pesquisarPorId(Long id) {
        Optional<Item> items = itemRepository.findById(id);
        return items;
    }

    @Override
    public List<Item> pesquisarPorNome(String name) {
       // List<Item> items = itemRepository.findItems(name);
        List<Item> items = itemRepository.findByNameContaining(name);
        return items;
    }

    @Override
    public Item criar(Item item) {
        Item itemSalva = itemRepository.save(item);
        return itemSalva;
    }

    @Override
    public Item atualizar(Long id, Item item) {
        Item itemSalva = buscarItemPorId(id);
        item.setId(id);
        BeanUtils.copyProperties(item, itemSalva);
        return itemRepository.save(itemSalva);
    }

    @Override
    public void remover(Long id){
        itemRepository.deleteById(id);
    }

    private Item buscarItemPorId(Long id){
        Item itemSalva = itemRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
        if(itemSalva == null){
            throw new EmptyResultDataAccessException(1);
        }
        return itemSalva;
    }

}
