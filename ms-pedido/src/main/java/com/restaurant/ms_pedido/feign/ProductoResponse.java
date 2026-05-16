package com.restaurant.ms_pedido.feign;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoResponse {
    private Long id;
    private String nombreProducto;
    private Double precio;
    private Boolean estadoProducto;
}
