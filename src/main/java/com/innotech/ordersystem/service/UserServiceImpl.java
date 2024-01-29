package com.innotech.ordersystem.service;

import com.innotech.ordersystem.model.User;
import com.innotech.ordersystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> listarTodos() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @Override
    public Optional<User> pesquisarPorId(Long id) {
        Optional<User> users = userRepository.findById(id);
        return users;
    }

    @Override
    public List<User> pesquisarPorNome(String name) {
        List<User> users = userRepository.findByName(name);
        return users;
    }

    @Override
    public User criar(User user) {
        log.info("USER: "+user.toString());
        User userSalva = userRepository.save(user);
        return userSalva;
    }

    @Override
    public User atualizar(Long id, User user) {
        User userSalva = buscarUserPorId(id);
        user.setId(id);
        BeanUtils.copyProperties(user, userSalva);
        return userRepository.save(userSalva);
    }

    @Override
    public void remover(Long id){
        userRepository.deleteById(id);
    }

    private User buscarUserPorId(Long id){
        User userSalva = userRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
        if(userSalva == null){
            throw new EmptyResultDataAccessException(1);
        }
        return userSalva;
    }

}
