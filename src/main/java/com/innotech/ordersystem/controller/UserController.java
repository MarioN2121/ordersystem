package com.innotech.ordersystem.controller;

import com.innotech.ordersystem.model.User;
import com.innotech.ordersystem.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/listartodos")
    public List<User> listar(){
        return userServiceImpl.listarTodos();
    }

    @GetMapping("/pesquisar-por-id/{id}")
    public ResponseEntity<Optional<User>> pesquisarPorId(@PathVariable Long id){
        Optional<User> user = userServiceImpl.pesquisarPorId(id);
        return user !=null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/pesquisar-por-nome/{nome}")
    public ResponseEntity<List<User>> pesquisarPorNome(@PathVariable String nome){
        List<User> users = userServiceImpl.pesquisarPorNome(nome);
        return users !=null ? ResponseEntity.ok(users) : ResponseEntity.notFound().build();
    }

    @PostMapping("/criar")
    public ResponseEntity<User> criar(@RequestBody User user){
        User userSalva = userServiceImpl.criar(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userSalva);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<User> atualizar(@PathVariable Long id, @RequestBody User user) {
        User userSalvo = userServiceImpl.atualizar(id, user);
        return ResponseEntity.ok(userSalvo);
    }

    @DeleteMapping("/remover/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        userServiceImpl.remover(id);
    }

}
