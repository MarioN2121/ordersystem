package com.innotech.ordersystem.service;

import com.innotech.ordersystem.model.*;
import com.innotech.ordersystem.repository.OrderRepository;
import com.innotech.ordersystem.repository.StockMovementRepository;
import com.innotech.ordersystem.repository.StockRepository;
import com.innotech.ordersystem.utils.GlobalUtils;
import com.innotech.ordersystem.utils.OrderStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StockMovementServiceImpl implements StockMovementService{

    @Autowired
    private StockMovementRepository stockMovementRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private OrderRepository orderRepository;

    //@Autowired
    //private OrderServiceImpl orderServiceImpl;

    @Autowired
    private EmailSendServiceImpl emailSendServiceImpl;

    @Override
    public List<StockMovement> listarTodos() {
        List<StockMovement> stockMovement = stockMovementRepository.findAll();
        return stockMovement;
    }

    @Override
    public Optional<StockMovement> pesquisarPorId(Long id) {
        Optional<StockMovement> stockMovement = stockMovementRepository.findById(id);
        return stockMovement;
    }


    @Override
    public StockMovement criar(StockMovement stockMovement) {
        StockMovement stockMovementSalva = stockMovementRepository.save(stockMovement);
        return stockMovementSalva;
    }

    @Override
    public StockMovement atualizar(Long id, StockMovement stockMovement) {

        StockMovement stockMovementSalva = buscarItemPorId(id);
        if(stockMovementSalva !=null){
            stockMovement.setId(id);
            BeanUtils.copyProperties(stockMovement, stockMovementSalva);
            stockMovementSalva = stockMovementRepository.save(stockMovementSalva);
        }
        return stockMovementSalva;
    }

    @Override
    public void remover(Long id) {
        stockMovementRepository.deleteById(id);
    }

    //TODO ... ACRESCENTAR MAIS METODOS PARA AS OPERAÇOES DE ORDER(COMPRA)

    private StockMovement buscarItemPorId(Long id){
        StockMovement stockSalva = stockMovementRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
        if(stockSalva == null){
            throw new EmptyResultDataAccessException(1);
        }
        return stockSalva;
    }

    @Override
    public List<StockMovement> pesquisarPorOrderId(Long orderId) {
        List<StockMovement> stockMovements = stockMovementRepository.findByOrderId(orderId);
        return stockMovements;
    }

    @Override
    public List<StockMovement> pesquisarPorStockId(Long stockId) {
        List<StockMovement> stockMovements = stockMovementRepository.findByStockId(stockId);
        return stockMovements;
    }
    @Override
    public StockMovement atualizarStock(Long stockId, int quantidade){
        StockMovement stockMovement = null;
        Stock stockSalvo = stockRepository.getById(stockId);
        if(stockSalvo!=null){
            if(stockSalvo.getQuantity()<0){
                //stockMovement = carregaStockAndOrder(stockSalvo,quantidade);
                //carregaStockAndOrderAux(stockSalvo,quantidade);
                System.out.println("ATUALIZACAO DO STOCK NEGATIVO : "+stockSalvo.getQuantity());
            }else{
               stockMovement = carregaStock(stockSalvo, quantidade);
            }
        }
        return stockMovement;
    }


    private StockMovement carregaStock(Stock stockSalvo, int quantidade){
        stockSalvo.setQuantity(stockSalvo.getQuantity()+quantidade);
        stockRepository.save(stockSalvo);
        StockMovement stockMovement = new StockMovement();
        stockMovement.setMovementDate(LocalDateTime.now());
        stockMovement.setStockId(stockSalvo.getId());
        stockMovement.setStockQuantity(stockSalvo.getQuantity());
        StockMovement stockMovementSalvo = stockMovementRepository.save(stockMovement);
        return stockMovementSalvo;
    }

    private StockMovement carregaStockAndOrder(Stock stock, int quantidade){
        StockMovement stockMovementSalvo = null;
        //stockMovementRepository.findByStockIdAndStockQuantity(stock.getId(), stock.getQuantity());
        //System.out.println("stockMovementSalvo STOCK ID: "+stock.toString());

        List<StockMovement> stockMovements = stockMovementRepository.findByStockId(stock.getId());
        for(StockMovement stm : stockMovements){
            if(stm.getStockQuantity() == stock.getQuantity()){
                stockMovementSalvo = stm;
            }
        }

       // System.out.println("stockMovementSalvo ORDER ID: "+stockMovementSalvo.toString());
        int valorAtualizado = stock.getQuantity()+quantidade;
        stock.setQuantity(valorAtualizado);
        stockRepository.save(stock);
        stockMovementSalvo.setStockQuantity(quantidade);
        StockMovement stockMovement = stockMovementRepository.save(stockMovementSalvo);

        System.out.println("ORDER ID: "+stockMovementSalvo.getOrderId());
        Long idOrder = stockMovementSalvo.getOrderId();
        //Order order = orderRepository.findById(stockMovementSalvo.getOrderId()).orElseThrow(() -> new EmptyResultDataAccessException(1));
        //TODO -... REVER ESTE METODO E REPARAR A SITUAÇÃO ...
        Order order = orderRepository.findById(idOrder).orElse(null);

        carregaOrder(order,valorAtualizado);


        return stockMovementSalvo;
    }



    public Order carregaOrder(Order order, int valorCalcualdo) {
        Order orderSalva = null;
        EmailSend emailSendSalva = null;

        System.out.println("*** VALOR PROCURADO: "+order.getUser().getName());
        System.out.println("*** VALOR PROCURADO: "+order.getUser().getEmail());

        if( valorCalcualdo >= 0){
            EmailSend emailSend =carregaEmailDataCompleto(order.getUser());
            emailSendSalva = emailSendServiceImpl.sendEmail(emailSend);
            order.setEmailStatus(emailSendSalva.getEmailStatus());
            order.setOrderStatus(OrderStatus.Completed);
            orderSalva = orderRepository.save(order);
        }else{
            EmailSend emailSend =carregaEmailDataIncompleto(order.getUser());
            emailSendSalva = emailSendServiceImpl.sendEmail(emailSend);
            order.setEmailStatus(emailSendSalva.getEmailStatus());
            order.setOrderStatus(OrderStatus.Incompleted);
            orderSalva = orderRepository.save(order);
        }
        return orderSalva;
    }


    private EmailSend carregaEmailDataCompleto(User user){
        EmailSend emailSend = new EmailSend();
        emailSend.setOwnerRef(user.getId()+"");
        emailSend.setEmailFrom(GlobalUtils.TECNICAL_EXERCICE_EMAIL);
        emailSend.setSubject(GlobalUtils.ORDER_COMPLETE_EMAIL_SUBJECT);
        emailSend.setText(GlobalUtils.ORDER_COMPLETE_EMAIL_TEXT.replace("[name]",user.getName()));
        emailSend.setEmailTo(user.getEmail());
        emailSend.setEmailDate(LocalDateTime.now());
        return emailSend;
    }

    private EmailSend carregaEmailDataIncompleto(User user){
        EmailSend emailSend = new EmailSend();
        emailSend.setOwnerRef(user.getId()+"");
        emailSend.setEmailFrom(GlobalUtils.TECNICAL_EXERCICE_EMAIL);
        emailSend.setSubject(GlobalUtils.ORDER_INCOMPLETE_EMAIL_SUBJECT);
        emailSend.setText(GlobalUtils.ORDER_INCOMPLETE_EMAIL_TEXT.replace("[name]",user.getName()));
        emailSend.setEmailTo(user.getEmail());
        emailSend.setEmailDate(LocalDateTime.now());
        return emailSend;
    }

    /*
    private StockMovement carregaStockAndOrder(Stock stock, int quantidade){
        StockMovement stockMovementSalvo = stockMovementRepository.findByStockIdAndStockQuantity(stock.getId(), stock.getQuantity());
        Order order = orderRepository.findById(stockMovementSalvo.getOrderId()).orElseThrow(() -> new EmptyResultDataAccessException(1));
        int valorAtualizado = stock.getQuantity()+quantidade;
        stock.setQuantity(valorAtualizado);
        stockRepository.save(stock);

        StockMovement stockMovementNew = new StockMovement();
        stockMovementNew.setMovementDate(LocalDateTime.now());
        stockMovementNew.setStockId(stock.getId());
        stockMovementNew.setStockQuantity(valorAtualizado);
        stockMovementNew.setOrderId(order.getId());
        stockMovementNew.setOrderName(stockMovementSalvo.getOrderName());

        if(valorAtualizado>=0){
            EmailSend emailSend = orderServiceImpl.carregaEmailDataCompleto(order.getUser());
            EmailSend emailSendSalva = emailSendServiceImpl.sendEmail(emailSend);
            order.setEmailStatus(emailSendSalva.getEmailStatus());
            order.setOrderStatus(OrderStatus.Completed);
            Order orderSalva = orderRepository.save(order);
        }
        StockMovement stockMovement = stockMovementRepository.save(stockMovementNew);

        return stockMovement;
    }*/

    public List<StockMovement> carregaStockAndOrderAux(Stock stock, int quantidade){
        List<StockMovement> stockMovementSalvo = stockMovementRepository.findByStockId(stock.getId());
        System.out.println("TESTE DE PESQUISA: "+stockMovementSalvo.toString());

        return stockMovementSalvo;
    }


}
