package com.innotech.ordersystem.service;

import com.innotech.ordersystem.model.EmailSend;
import org.springframework.stereotype.Service;

@Service
public interface EmailSendService {

   public EmailSend sendEmail(EmailSend emailSend);
}
