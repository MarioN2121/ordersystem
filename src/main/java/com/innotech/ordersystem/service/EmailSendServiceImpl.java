package com.innotech.ordersystem.service;

import com.innotech.ordersystem.model.EmailSend;
import com.innotech.ordersystem.repository.EmailSendRepository;
import com.innotech.ordersystem.utils.EmailStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailSendServiceImpl implements EmailSendService{


    @Autowired
    private EmailSendRepository emailSendRepository;

    @Autowired
    private JavaMailSender senderMail;


    @Override
    public EmailSend sendEmail(EmailSend emailSend) {
        emailSend.setEmailDate(LocalDateTime.now());
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailSend.getEmailFrom());
            message.setTo(emailSend.getEmailTo());
            message.setSubject(emailSend.getSubject());
            message.setText(emailSend.getText());
            senderMail.send(message);

            emailSend.setEmailStatus(EmailStatus.Sent);
        }catch (MailException e){
            emailSend.setEmailStatus(EmailStatus.Error);
        }finally {
            return emailSendRepository.save(emailSend);
        }
    }



}
