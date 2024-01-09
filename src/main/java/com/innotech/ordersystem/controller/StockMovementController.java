package com.innotech.ordersystem.controller;

import com.innotech.ordersystem.model.StockMovement;
import com.innotech.ordersystem.service.StockMovementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stockMovements")
public class StockMovementController {


    @Autowired
    private StockMovementServiceImpl stockMovementServiceImpl;


    @GetMapping("/listartodos")
    public List<StockMovement> listar(){
        return stockMovementServiceImpl.listarTodos();
    }

    @GetMapping("/pesquisar-por-id/{id}")
    public ResponseEntity<Optional<StockMovement>> pesquisarPorId(@PathVariable Long id){
        Optional<StockMovement> stockMovement = stockMovementServiceImpl.pesquisarPorId(id);
        return stockMovement !=null ? ResponseEntity.ok(stockMovement) : ResponseEntity.notFound().build();
    }

    @GetMapping("/pesquisar-por-nome/{nome}")
    public ResponseEntity<List<StockMovement>> pesquisarPorNome(@PathVariable String nome){
        List<StockMovement> stockMovements = stockMovementServiceImpl.pesquisarPorNome(nome);
        return stockMovements !=null ? ResponseEntity.ok(stockMovements) : ResponseEntity.notFound().build();
    }

    @PostMapping("/criar")
    public ResponseEntity<StockMovement> criar(@RequestBody StockMovement stockMovement){
        StockMovement stockMovementSalva = stockMovementServiceImpl.criar(stockMovement);
        return ResponseEntity.status(HttpStatus.CREATED).body(stockMovementSalva);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<StockMovement> atualizar(@PathVariable Long id, @RequestBody StockMovement stockMovement) {
        StockMovement stockMovementSalva = stockMovementServiceImpl.atualizar(id, stockMovement);
        return stockMovementSalva !=null ? ResponseEntity.ok(stockMovementSalva) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remover/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        stockMovementServiceImpl.remover(id);
    }

    //TODO ... ACRESCENTAR MAIS METODOS PARA AS OPERAÇOES DE ORDER(COMPRA)

}
