package com.senac.microservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeCliente;

    @Column(nullable = false)
    private BigDecimal valorItens;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    
}
