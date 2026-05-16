package com.restaurant.ms_pedido.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.ms_pedido.dto.PedidoDTO;
import com.restaurant.ms_pedido.model.Pedido;
import com.restaurant.ms_pedido.service.PedidoService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService pedidoService;

    public PedidoController (PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping()
    public ResponseEntity <List<PedidoDTO>>getListar() {
        List<Pedido> pedidos = pedidoService.listar();
        List<PedidoDTO> dtos = pedidos.stream().map(PedidoDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/exists")
	public ResponseEntity<Boolean> existePedido(@PathVariable Long id) {
		return ResponseEntity.ok(pedidoService.findbyId(id));
	}

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> obtenerPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscar(id);
        return ResponseEntity.ok(PedidoDTO.fromModel(pedido));
    }

    @PostMapping
    public ResponseEntity<PedidoDTO> crearProducto(@RequestBody PedidoDTO pedidoDTO) {
        Pedido nuevo = pedidoService.save(pedidoDTO.toModel());
        return ResponseEntity.ok(PedidoDTO.fromModel(nuevo));
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<PedidoDTO> putModificarProducto(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        Pedido resultado = pedidoService.modificar(id, pedidoDTO.toModel()); //se llama .toModel() porque en el service tenemos que debe recibir un pedido no un pedidoDTO.

        if (resultado != null) {
            return ResponseEntity.ok(PedidoDTO.fromModel(resultado));
        }
        return ResponseEntity.notFound().build();
    }

    
}
