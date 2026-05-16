package com.restaurant.ms_producto.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.ms_producto.dto.ProductoDTO;
import com.restaurant.ms_producto.model.Producto;
import com.restaurant.ms_producto.service.ProductoService;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

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

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> obtenerPorId(@PathVariable Long id) {
        Producto producto = productoService.buscarPorId(id);
        return ResponseEntity.ok(ProductoDTO.fromModel(producto));
    }

    @GetMapping("/{id}/exists")
	public ResponseEntity<Boolean> existeProducto(@PathVariable Long id) {
		return ResponseEntity.ok(productoService.existeid(id));
	}

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<ProductoDTO>> buscarPorCategoria(@PathVariable Long categoriaId) {
    List<Producto> productos = productoService.buscarPorCategoria(categoriaId);
    if (productos == null) return ResponseEntity.notFound().build();
    List<ProductoDTO> dtos = productos.stream()
            .map(ProductoDTO::fromModel)
            .collect(Collectors.toList());
    return ResponseEntity.ok(dtos);
    }
    
    @GetMapping("/activos")
    public ResponseEntity<List<ProductoDTO>> listarActivos() {
        List<Producto> productos = productoService.listarSoloActivos();
        List<ProductoDTO> dtos = productos.stream()
            .map(ProductoDTO::fromModel)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@RequestBody ProductoDTO productoDTO) {
        Producto nuevo = productoService.save(productoDTO);
        return ResponseEntity.ok(ProductoDTO.fromModel(nuevo));
    }

    @PutMapping("/modificar/{id}")
    public ResponseEntity<ProductoDTO> putModificarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        Producto resultado = productoService.modificar(id, productoDTO); // ← ya no se llama .toModel()

        if (resultado != null) {
            return ResponseEntity.ok(ProductoDTO.fromModel(resultado));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        try {
            productoService.desactivarProducto(id);
            return ResponseEntity.noContent().build(); // Retorna 204 (Éxito, sin contenido)
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 si el ID no existe
        }
    }
    
}
