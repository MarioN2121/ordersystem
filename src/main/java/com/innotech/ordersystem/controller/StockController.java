package com.innotech.ordersystem.controller;

import com.innotech.ordersystem.model.Stock;
import com.innotech.ordersystem.service.StockServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stocks")
public class StockController {

    @Autowired
    private StockServiceImpl stockServiceImpl;


    @GetMapping("/listartodos")
    public List<Stock> listar(){
        return stockServiceImpl.listarTodos();
    }

    @GetMapping("/pesquisar-por-id/{id}")
    public ResponseEntity<Optional<Stock>> pesquisarPorId(@PathVariable Long id){
        Optional<Stock> stock = stockServiceImpl.pesquisarPorId(id);
        return stock !=null ? ResponseEntity.ok(stock) : ResponseEntity.notFound().build();
    }

    @PostMapping("/criar")
    public ResponseEntity<Stock> criar(@RequestBody Stock stock){
        Stock stocksSalva = stockServiceImpl.criar(stock);
        return ResponseEntity.status(HttpStatus.CREATED).body(stocksSalva);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Stock> atualizar(@PathVariable Long id, @RequestBody Stock stock) {
        Stock stockSalva = stockServiceImpl.atualizar(id, stock);
        return ResponseEntity.ok(stockSalva);
    }

    @DeleteMapping("/remover/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        stockServiceImpl.remover(id);
    }


}
