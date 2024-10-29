package com.senac.microservice.messaging;

import com.senac.microservice.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class PagamentoConsumer {

    @Autowired
    private PedidoService pedidoService;

    @JmsListener(destination = "pagamento.fila")
    public void receberPagamento(String mensagem) {
        String[] partes = mensagem.split(",");
        Long pedidoId = Long.parseLong(partes[0]);
        String formaPagamento = partes[1];
        pedidoService.atualizarFormaPagamento(pedidoId, formaPagamento);
    }
}
