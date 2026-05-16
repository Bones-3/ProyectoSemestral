package com.restaurant.ms_pedido.controller;

import org.springframework.web.bind.annotation.RestController;

import com.restaurant.ms_pedido.dto.DetallePedidoDTO;
import com.restaurant.ms_pedido.model.DetallePedido;
import com.restaurant.ms_pedido.service.DetallePedidoService;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/detallepedidos")
public class DetallePedidoController {
    private final DetallePedidoService detallePedidoService;

    public DetallePedidoController (DetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    @GetMapping()
    public ResponseEntity <List<DetallePedidoDTO>> getListarDetallePedido() {
        List<DetallePedido> detallepedidos = detallePedidoService.listar();
        List<DetallePedidoDTO> dtos = detallepedidos.stream().map(DetallePedidoDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/exists")
	public ResponseEntity<Boolean> existeDetallePedido(@PathVariable Long id) {
		return ResponseEntity.ok(detallePedidoService.find(id));
	}

    @GetMapping("/{id}")
    public ResponseEntity<DetallePedidoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(DetallePedidoDTO.fromModel(detallePedidoService.buscar(id)));
    }

    @PostMapping()
	public ResponseEntity<DetallePedidoDTO> crearCategoria(@RequestBody DetallePedidoDTO detallePedidoDTO) {
	    DetallePedido nuevo = detallePedidoService.saveDetallePedido(detallePedidoDTO.toModel());
		return ResponseEntity.ok(DetallePedidoDTO.fromModel(nuevo));
	}

    @PutMapping("/modificar/{id}")
    public ResponseEntity<DetallePedidoDTO> putModificarProducto(@PathVariable Long id, @RequestBody DetallePedidoDTO detallePedidoDTO) {
        DetallePedido resultado = detallePedidoService.modificar(id, detallePedidoDTO.toModel());

        if (resultado != null) {
            return ResponseEntity.ok(DetallePedidoDTO.fromModel(resultado));
        }
        return ResponseEntity.notFound().build();
    }
    
}