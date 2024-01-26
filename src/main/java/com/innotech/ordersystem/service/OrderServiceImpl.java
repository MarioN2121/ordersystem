package com.innotech.ordersystem.service;

import com.innotech.ordersystem.model.EmailSend;
import com.innotech.ordersystem.model.Order;
import com.innotech.ordersystem.model.Stock;
import com.innotech.ordersystem.model.User;
import com.innotech.ordersystem.repository.OrderRepository;
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
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StockServiceImpl stockServiceImpl;

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
        //TODO ... - when an order is created, it should try to satisfy it with the current stock.;
        // se a Order for superior ao stock entao faz subtração e fica em negativo
        Order orderSalva = null;
        EmailSend emailSendSalva = null;
        Stock stock =  stockServiceImpl.buscarStockPorItemId(order.getItem().getId());
        int valorCalcualdo = calcularOrder(order.getQuantity(),stock.getQuantity());
        if( valorCalcualdo >= 0){
            EmailSend emailSend =carregaEmailData(order.getUser());
            emailSendSalva = emailSendServiceImpl.sendEmail(emailSend);
            order.setOrderStatus(OrderStatus.Completed);
            order.setEmailStatus(emailSendSalva.getEmailStatus());
            orderSalva = orderRepository.save(order);
            stock.setQuantity(valorCalcualdo);
            stockServiceImpl.atualizar(stock.getId(),stock);
        }else{
            System.out.println("VALOR: "+valorCalcualdo);

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

    private EmailSend carregaEmailData(User user){
        EmailSend emailSend = new EmailSend();
        emailSend.setOwnerRef(user.getId()+"");
        emailSend.setEmailFrom(GlobalUtils.TECNICAL_EXERCICE_EMAIL);
        emailSend.setSubject(GlobalUtils.ORDER_COMPLETE_EMAIL_SUBJECT);
        emailSend.setText(GlobalUtils.ORDER_COMPLETE_EMAIL_TEXT.replace("[name]",user.getName()));
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

}
