package com.restaurant.ms_pedido.dto;

import com.restaurant.ms_pedido.model.DetallePedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DetallePedidoDTO {
    private Long id;

    private Long productoId;

    private Integer cantidad;

    private double precioUnitario;

    private double subtotal;

    private  String notasPedido;

    public DetallePedido toModel() {
        return new DetallePedido(id, productoId, cantidad, precioUnitario, subtotal, notasPedido, null);
    }

    public static DetallePedidoDTO fromModel (DetallePedido d) {
        if(d == null) return null;
        return new DetallePedidoDTO(d.getId(), d.getProductoId(), d.getCantidad(), d.getPrecioUnitario(), d.getSubtotal(), d.getNotasPedido());
    }
}
