package com.innotech.ordersystem.controller;

import com.innotech.ordersystem.DTO.EmailDTO;
import com.innotech.ordersystem.model.EmailSend;
import com.innotech.ordersystem.service.EmailSendServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/emailsender")
public class EmailSendController {

    @Autowired
    private EmailSendServiceImpl EmailSendServiceImpl;


    @PostMapping("/sending-email")
    public ResponseEntity<EmailSend> sendidngEmail(@RequestBody @Valid EmailDTO emailDTO){
        EmailSend emailSend = new EmailSend();
        BeanUtils.copyProperties(emailDTO, emailSend);
        EmailSendServiceImpl.sendEmail(emailSend);
        return new ResponseEntity<>(emailSend, HttpStatus.CREATED);
    }



}
