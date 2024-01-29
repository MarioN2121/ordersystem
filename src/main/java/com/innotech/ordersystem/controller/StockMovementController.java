package com.innotech.ordersystem.controller;

import com.innotech.ordersystem.model.Stock;
import com.innotech.ordersystem.model.StockMovement;
import com.innotech.ordersystem.service.StockMovementServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stockmovements")
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

    @PostMapping("/criar")
    public ResponseEntity<StockMovement> criar(@RequestBody StockMovement stockMovement){
        StockMovement stockMovemenSalva = stockMovementServiceImpl.criar(stockMovement);
        return ResponseEntity.status(HttpStatus.CREATED).body(stockMovemenSalva);
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<StockMovement> atualizar(@PathVariable Long id, @RequestBody StockMovement stockMovement) {
        StockMovement stockMovemenSalva = stockMovementServiceImpl.atualizar(id, stockMovement);
        return stockMovemenSalva !=null ? ResponseEntity.ok(stockMovemenSalva) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/remover/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id){
        stockMovementServiceImpl.remover(id);
    }

    //TODO ... ACRESCENTAR MAIS METODOS PARA AS OPERAÇOES DE ORDER(COMPRA)

    @GetMapping("/orders/pesquisar-por-id/{id}")
    public ResponseEntity<List<StockMovement>> pesquisarPorOrderId(@PathVariable Long id){
        List<StockMovement> stockMovement = stockMovementServiceImpl.pesquisarPorOrderId(id);
        return stockMovement !=null ? ResponseEntity.ok(stockMovement) : ResponseEntity.notFound().build();
    }

    @GetMapping("/stocks/pesquisar-por-id/{id}")
    public ResponseEntity<List<StockMovement>> pesquisarPorStockId(@PathVariable Long id){
        List<StockMovement> stockMovement = stockMovementServiceImpl.pesquisarPorStockId(id);
        return stockMovement !=null ? ResponseEntity.ok(stockMovement) : ResponseEntity.notFound().build();
    }

    @PutMapping("/atualizar/stock/{id}")
    public ResponseEntity<StockMovement> atualizarStock(@PathVariable Long id, @RequestBody Stock stock){
        StockMovement stockMovement = stockMovementServiceImpl.atualizarStock(id,stock.getQuantity());
        return stockMovement != null ? ResponseEntity.ok(stockMovement) : ResponseEntity.notFound().build();
    }

}
