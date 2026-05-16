package com.restaurant.ms_pago.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MS-USUARIO")
public interface UsuarioFeign {
    @GetMapping("/usuarios/{id}/exists")
    Boolean existeUsuario(@PathVariable("id") Long id);
}
