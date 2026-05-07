package com.restaurant.ms_producto.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.ms_producto.dto.ProductoDTO;
import com.restaurant.ms_producto.model.Producto;
import com.restaurant.ms_producto.service.ProductoService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController (ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public ResponseEntity <List<ProductoDTO>> listarProductos() {
        List<Producto> productos = productoService.listar();
        List<ProductoDTO> dtos = productos.stream().map(ProductoDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}/exists")
	public ResponseEntity<Boolean> existeProducto(@PathVariable Long id) {
		return ResponseEntity.ok(productoService.existeid(id));
	}

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO productoDTO) {
    Producto nuevo = productoService.save(productoDTO);
    return ResponseEntity.status(201).body(ProductoDTO.fromModel(nuevo));
    }
    
    
}
