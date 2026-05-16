package com.restaurant.ms_pago.dto;

import java.time.LocalDateTime;

import com.restaurant.ms_pago.model.Pago;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PagoDTO {
    private Long id;
    private Long pedido_id;
    private Long usuario_id;
    private Double monto;
    private String metodoPago;
    private LocalDateTime fechaPago;
 
    public Pago toModel() {
        return new Pago(id, pedido_id, usuario_id, monto, metodoPago, fechaPago);
    }
 
    public static PagoDTO fromModel(Pago resultado) {
        if (resultado == null) return null;
        return new PagoDTO(
            resultado.getId(),
            resultado.getPedido_id(),
            resultado.getUsuario_id(),
            resultado.getMonto(),
            resultado.getMetodoPago(),
            resultado.getFechaPago()
        );
    }
}

