package com.restaurant.ms_pedido.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.restaurant.ms_pedido.model.Pedido;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PedidoDTO {
    private Long id;
    private Long usuarioId;
    private LocalDateTime fechaPedido;
    private Double total;
    private String tipoEntrega;
    private List<DetallePedidoDTO> detalles;

    public Pedido toModel() {
    return new Pedido(id, usuarioId, fechaPedido, total, tipoEntrega, null);
    }

    public static PedidoDTO fromModel(Pedido p) {
        if (p == null) return null;
        return PedidoDTO.builder()
                .id(p.getId())
                .usuarioId(p.getUsuarioId())
                .fechaPedido(p.getFechaPedido())
                .total(p.getTotal())
                .tipoEntrega(p.getTipoEntrega())
                .build();
    }
}
