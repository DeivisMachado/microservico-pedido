package com.senac.microservice.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import com.senac.microservice.service.PedidoService;

@Component
@Slf4j
@RequiredArgsConstructor
public class AtualizacaoPagamentoConsumer {

    private final PedidoService pedidoService;

    @JmsListener(destination = "fila.atualizacao-pedidos")
    public void receberAtualizacaoPagamento(String mensagem) {
        try {
            String[] dados = mensagem.split(",");
            Long pedidoId = Long.parseLong(dados[0]);
            String status = dados[1];
            String formaPagamento = dados[2];

            log.info("Recebido pedido para atualização de pagamento: {}", pedidoId);
            pedidoService.atualizarStatusPagamento(pedidoId, status);
            
            log.info("Status do pagamento atualizado para o pedido {}: {} - {}", pedidoId, status);

            pedidoService.atualizarFormaPagamento(pedidoId, formaPagamento);
            log.info("Forma de pagamento atualizado para o pedido {}: {}", pedidoId, formaPagamento);
            
        } catch (Exception e) {
            log.error("Erro ao processar atualização de pagamento: {}", mensagem, e);
        }
    }
}
