package com.innotech.ordersystem.service;

import com.innotech.ordersystem.model.Item;
import com.innotech.ordersystem.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<User> listarTodos();
    public Optional<User> pesquisarPorId(Long id);
    public List<User> pesquisarPorNome(String name);
    public User criar(User user);
    public User atualizar(Long id, User user);
    public void remover(Long id);

}
