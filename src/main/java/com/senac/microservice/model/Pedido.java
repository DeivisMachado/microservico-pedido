package com.senac.microservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nomeCliente;
    private BigDecimal valorItens;
    private BigDecimal valorTotal;
    private String statusPagamento;
    private String formaPagamento;
    
    // getters e setters
}
