package com.innotech.ordersystem.repository;

import com.innotech.ordersystem.model.EmailSend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface EmailSendRepository extends JpaRepository<EmailSend, Long> {

}
