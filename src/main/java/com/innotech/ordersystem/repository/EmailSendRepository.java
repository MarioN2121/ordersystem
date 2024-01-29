package com.innotech.ordersystem.repository;

import com.innotech.ordersystem.model.EmailSend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailSendRepository extends JpaRepository<EmailSend, Long> {

}
