package com.restaurant.ms_pedido.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restaurant.ms_pedido.model.DetallePedido;

@Repository
public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long>{
    // Busca todos los detalles de un pedido específico
    List<DetallePedido> findByPedidoId(Long pedidoId);
}
