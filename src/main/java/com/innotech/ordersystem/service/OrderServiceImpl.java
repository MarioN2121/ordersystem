package com.innotech.ordersystem.service;

import com.innotech.ordersystem.model.*;
import com.innotech.ordersystem.repository.OrderRepository;
import com.innotech.ordersystem.utils.GlobalUtils;
import com.innotech.ordersystem.utils.OrderStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StockServiceImpl stockServiceImpl;

    @Autowired
    private StockMovementServiceImpl stockMovementServiceImpl;

    @Autowired
    private EmailSendServiceImpl emailSendServiceImpl;

    @Override
    public List<Order> listarTodos() {
        List<Order> orders = orderRepository.findAll();
        return orders;
    }

    @Override
    public Optional<Order> pesquisarPorId(Long id) {
        Optional<Order> orders = orderRepository.findById(id);
        return orders;
    }

    @Override
    public List<Order> pesquisarPorNome(String nome) {
        List<Order> orders = orderRepository.findByItemNameContaining(nome);  //findByItemContaining(item);
        return orders;
    }

    @Override
    public Order criar(Order order) {
        Order orderSalva = null;
        EmailSend emailSendSalva = null;
        Stock stock =  stockServiceImpl.buscarStockPorItemId(order.getItem().getId());
        int valorCalcualdo = calcularOrder(order.getQuantity(),stock.getQuantity());

        if( valorCalcualdo >= 0){
            EmailSend emailSend =carregaEmailDataCompleto(order.getUser());
            emailSendSalva = emailSendServiceImpl.sendEmail(emailSend);
            order.setCreationDate(LocalDate.now());
            order.setEmailStatus(emailSendSalva.getEmailStatus());

            order.setOrderStatus(OrderStatus.Completed);
            orderSalva = orderRepository.save(order);
            stock.setQuantity(valorCalcualdo);
            stockServiceImpl.atualizar(stock.getId(),stock);
            carregaStockMovement(order,stock);
        }else{
            EmailSend emailSend =carregaEmailDataIncompleto(order.getUser());
            emailSendSalva = emailSendServiceImpl.sendEmail(emailSend);
            order.setCreationDate(LocalDate.now());
            order.setEmailStatus(emailSendSalva.getEmailStatus());

            order.setOrderStatus(OrderStatus.Incompleted);
            orderSalva = orderRepository.save(order);
            stock.setQuantity(valorCalcualdo);
            stockServiceImpl.atualizar(stock.getId(),stock);
            carregaStockMovement(order,stock);
        }
        return orderSalva;
    }

    @Override
    public Order atualizar(Long id, Order order) {
        Order orderSalva = buscarOrderPorId(id);
        order.setId(id);
        BeanUtils.copyProperties(order, orderSalva);
        return orderRepository.save(orderSalva);
    }

    @Override
    public void remover(Long id) {
        orderRepository.deleteById(id);
    }

    private int calcularOrder(int orderQuantity, int stockQuantity){
        return stockQuantity-orderQuantity;
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

    private Order buscarOrderPorId(Long id){
        Order orderSalva = orderRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
        if(orderSalva == null){
            throw new EmptyResultDataAccessException(1);
        }
        return orderSalva;
    }

    private void carregaStockMovement(Order order, Stock stock){
        StockMovement stockMovement = new StockMovement();
        stockMovement.setId(1L);
        stockMovement.setMovementDate(LocalDateTime.now());
        stockMovement.setOrderId(order.getId());
        stockMovement.setOrderQuantity(order.getQuantity());
        stockMovement.setOrderName(order.getItem().getName());
        stockMovement.setStockId(stock.getId());
        stockMovement.setStockQuantity(stock.getQuantity());
        stockMovementServiceImpl.criar(stockMovement);
    }


    public Order criar_aux(Order order, int valorCalcualdo) {
        Order orderSalva = null;
        EmailSend emailSendSalva = null;
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

}
