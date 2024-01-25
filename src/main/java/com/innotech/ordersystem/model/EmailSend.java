package com.innotech.ordersystem.model;

import com.innotech.ordersystem.utils.EmailStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "email")
public class EmailSend {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ownerRef;//passamos o id do user q está a ser enviado
    private String emailFrom;
    private String emailTo;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String text;
    private LocalDateTime emailDate;
    private EmailStatus emailStatus;

}
