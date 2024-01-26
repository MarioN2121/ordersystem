package com.innotech.ordersystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data()
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "stock_movement")
public class StockMovement {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime movementDate;

    private Long orderId;
    private String orderName;
    private int orderQuantity; //quantidade obrigatória

    private Long stockId;
    private int stockQuantity; //quantidade por satisfazer




}
