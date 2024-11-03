package com.senac.microservice.service;

import com.senac.microservice.model.Pedido;
import com.senac.microservice.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import java.util.List;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private JmsTemplate jmsTemplate;

    public Pedido criar(Pedido pedido) {
        pedido.setStatusPagamento("PENDENTE");
       

        Pedido pedidoSalvo = pedidoRepository.save(pedido);

        String mensagem = String.format("%d,%.2f", 
            pedidoSalvo.getId(), 
            pedidoSalvo.getValorTotal());
            
        jmsTemplate.convertAndSend("fila.pedidos", mensagem);
        
        return pedidoSalvo;
    }
    

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido atualizarFormaPagamento(Long pedidoId, String formaPagamento) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
            
        pedido.setFormaPagamento(formaPagamento);
        return pedidoRepository.save(pedido);
    }
    
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    @Transactional
    public void atualizarStatusPagamento(Long pedidoId, String status) {
        Pedido pedido = pedidoRepository.findById(pedidoId)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado: " + pedidoId));
            
        pedido.setStatusPagamento(status);
        pedidoRepository.save(pedido);
    }
}
