package com.restaurant.ms_pago.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MS-PEDIDO")
public interface PedidoFeign {
    @GetMapping("/pedidos/{id}/exists")
    Boolean existePedido(@PathVariable("id") Long id);
}
