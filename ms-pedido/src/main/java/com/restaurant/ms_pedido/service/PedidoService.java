package com.restaurant.ms_pedido.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.restaurant.ms_pedido.model.Pedido;
import com.restaurant.ms_pedido.repository.PedidoRepository;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    public PedidoService (PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> listar () {
        return pedidoRepository.findAll();
    }

    public boolean findbyId (Long id) {
        return pedidoRepository.existsById(id);
    }

    public Pedido buscar(Long id) {
        return pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    public Pedido save (Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public Pedido modificar (Long id, Pedido pedido) {
    return pedidoRepository.findById(id).map(pedidoExiste -> {
        pedidoExiste.setTipoEntrega(pedido.getTipoEntrega());
        return pedidoRepository.save(pedidoExiste);
    }).orElseThrow(() -> new RuntimeException("No se encontro"));
    }

    
}
