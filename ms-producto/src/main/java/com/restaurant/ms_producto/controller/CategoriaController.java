package com.restaurant.ms_producto.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.restaurant.ms_producto.dto.CategoriaDTO;
import com.restaurant.ms_producto.model.Categoria;
import com.restaurant.ms_producto.service.CategoriaService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController (CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping()
    public ResponseEntity <List<CategoriaDTO>> listarCategorias() {
        List<Categoria> productos = categoriaService.listar();
        List<CategoriaDTO> dtos = productos.stream().map(CategoriaDTO::fromModel).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(CategoriaDTO.fromModel(categoriaService.buscarPorId(id)));
    }

    @GetMapping("/{id}/exists")
	public ResponseEntity<Boolean> existeCategoria(@PathVariable Long id) {
		return ResponseEntity.ok(categoriaService.existeId(id));
	}

    @PostMapping()
	public ResponseEntity<CategoriaDTO> crearCategoria(@RequestBody CategoriaDTO categoriaDTO) {
	    Categoria nuevo = categoriaService.save(categoriaDTO.toModel());
		return ResponseEntity.ok(CategoriaDTO.fromModel(nuevo));
	}

    @PutMapping("/modificar/{id}")
    public ResponseEntity<CategoriaDTO> putModificarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
    
        // 1. Convertimos el DTO que llega a modelo usando tu método toModel()
        Categoria categoriaParaActualizar = categoriaDTO.toModel();

        // 2. Llamamos al Service (que ya tienes creado)
        Categoria resultado = categoriaService.modificar(id, categoriaParaActualizar);

        // 3. Si el resultado no es null, usamos tu método estático fromModel() para responder
        if (resultado != null) {
            return ResponseEntity.ok(CategoriaDTO.fromModel(resultado));
        }
    
        // Si el producto no existía (null), devolvemos 404
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desactivar(@PathVariable Long id) {
        try {
            categoriaService.desactivarCategoria(id);
            return ResponseEntity.noContent().build(); // Retorna 204 (Éxito, sin contenido)
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // Retorna 404 si el ID no existe
        }
    }
    

}
