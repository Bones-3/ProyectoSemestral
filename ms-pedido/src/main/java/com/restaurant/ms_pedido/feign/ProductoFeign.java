package com.restaurant.ms_pedido.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("MS-PRODUCTO")
public interface ProductoFeign {
    @GetMapping("/productos/{id}/exists")
    Boolean existeProducto(@PathVariable("id") Long id);
 
    @GetMapping("/productos/{id}")
    ProductoResponse obtenerProducto(@PathVariable("id") Long id);
}
