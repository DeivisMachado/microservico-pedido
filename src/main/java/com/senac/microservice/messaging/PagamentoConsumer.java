package com.senac.microservice.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.senac.microservice.service.PedidoService;

@Component
public class PagamentoConsumer {

    @Autowired
    private PedidoService pedidoService;

    @JmsListener(destination = "fila.pedidos.resposta")
    public void receberRespostaPagamento(String mensagem) {
        String[] dados = mensagem.split(",");
        Long pedidoId = Long.parseLong(dados[0]);
        String formaPagamento = dados[1];
        
        pedidoService.atualizarFormaPagamento(pedidoId, formaPagamento);
    }
}